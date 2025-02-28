package com.f.basic.model.cache;

import java.util.Hashtable;
import java.util.Map;

/** 
 * Represents an Object cache.   Grand(1998)
 */
public class Cache<K,T>   // K= Key type,  T=Cached object type
{

  /**
   * cache                    - the cache
   * mru                      - most recently used object.  Head of double chained list
   * lru                      - least recently used object. Tail of double chained list
   * currentCacheSize         - current size of cache
   * DEFAULT_MAX_CACHE_SIZE   - default size of cache
   */
  private Map<K,LinkedList<K,T>>  cache;
  private LinkedList<K,T>         mru;
  private LinkedList<K,T>         lru;
  private int                     currentCacheSize;
  private int                     maxSize;

  private final static int DEFAULT_MAX_CACHE_SIZE = 100;
  private final static int DEFAULT_MIN_CACHE_SIZE =  30;

  /** 
   * Gets an instance of cache 
   */
  public Cache( ) { 
	  reset( DEFAULT_MAX_CACHE_SIZE);
  }
  

  /**
   * Gets an instance of cache 
   * @param size Size of cache.
   */
  public Cache( int size) { 
	  reset( size >= DEFAULT_MIN_CACHE_SIZE? size : DEFAULT_MAX_CACHE_SIZE );
  }

  /**
   * Resets the cache
   * @param size Size of cache.
   * @throw IllegalArgumentException when {@code size <= 0}
   */
  public void reset( int size){
    if ( size <= 0 ) {
      throw new IllegalArgumentException("Invalid cache size["+ size+ "]. Cache size must be a positive number");
    }
    this.maxSize          = size;
    this.cache            = new Hashtable<>((int)(size * 1.33), (float)0.75);
    this.mru              = null;
    this.lru              = null;
    this.currentCacheSize = 0;
  } 


  /**
   * Adds a new object to the cache.
   * When cache determines it, based on its policy,
   * objects can be deleted from it to make room for new objects
   * @param key - Key that identifies the object to be added
   * @param obj - The candidate for addition to the cache
   */
  public synchronized void add(K key, T obj) {
    if ( cache.get(key) == null ){
      LinkedList<K,T> newLink = new LinkedList<>();
      newLink.key             = key;
      newLink.object          = obj;
      newLink.next            = mru;
      newLink.previous        = null;
      if ( mru != null) {
        mru.previous = newLink;
      }
      if ( lru == null){
        lru = newLink;
      }else if( currentCacheSize >= maxSize){
          cache.remove(lru.key);
          lru      = lru.previous;
          lru.next = null;
          currentCacheSize --;
      }
      mru = newLink;
      cache.put(key, newLink);
      currentCacheSize++;
    } else {  // add should not be called for existing objects. Make the object the MRU
      fetch(key);
    }

  }


  /**
   * Gets an object based on its Identifier
   * @param key Object Identifier 
   * @return Requested object if found; null otherwise
   */
  public synchronized T fetch(K key){
    LinkedList<K,T> foundLink = cache.get(key);
    if ( foundLink == null ) {
      return null;
    }
    
    // Make the object the mru
    if ( mru != foundLink ){
      foundLink.previous.next = foundLink.next;
      if ( foundLink.next != null ) {
        foundLink.next.previous = foundLink.previous;
      }
      if ( lru == foundLink) {
        lru = foundLink.previous;
      }
      foundLink.previous = null;
      foundLink.next     = mru;
      mru.previous       = foundLink;
      mru                = foundLink;
    } // if currentCacheSize > 1

    return foundLink.object;

  }


  /**
   * Replaces a cache object with another 
   * @param key    Id of object to be replaced
   * @param object Replacement object
   * @return Object older version, if found; null if not found
   */
  public synchronized T replace( K key, T object){
    LinkedList<K,T> foundLink = cache.get(key);
    if ( foundLink == null ) {
      return null;
    }
    T oldObject = foundLink.object;
    foundLink.object  = object;
    fetch( key);
    return oldObject;
  }


  /**
   * Removes an object from cache
   * @param key Identifier of object to be removed
   * @return Removed object, if found; null otherwise
   */
  public synchronized T remove( K key)
  {
    LinkedList<K,T> foundLink = cache.remove(key);
    if ( foundLink == null ) {
      return null;
    }
    
    // Remove the object from the list
    if ( mru == lru ){  // the only object in the cache
      mru     =   lru  = null;
      currentCacheSize = 0;
    } else if ( mru == foundLink ){ // most used
      mru          = mru.next;
      mru.previous = null;
    } else if ( lru == foundLink ){  // least used
      lru      = lru.previous;
      lru.next = null;
    } else{  // halfway in the list
      foundLink.next.previous = foundLink.previous;
      foundLink.previous.next = foundLink.next;
    }

    currentCacheSize--;
    return foundLink.object;
  }//remove(key)

  /**
   * String projection of the cache
   * @return String representation of cache
   */
  public synchronized String toString()
  {
    StringBuilder b = new StringBuilder();
    b.append("[Cache - size("+ cache.keySet().size()+ ")")
     .append( " maxSize("+ maxSize+ ")")
     .append( " mru(").append(mru == null? "null": (mru.object == null? "null": mru.key)).append(")")
     .append( " lru(").append(lru == null? "null": (lru.object == null? "null": lru.key)).append(")")
     .append( "\n");

    for( LinkedList<K,T> node= mru; node != null; node = node.next) {
      b.append(" Objeto (") .append(node.object == null? "null" : node.key).append(") ")
       .append(" next(")    .append(node.next == null? "null": node.next.key).append(") ")
       .append(" previous(").append(node.previous == null? "null" : node.previous.key).append(")\n");
    }
    b.append("]\n");
    return b.toString();

  }

  /**
   * Clears the cache
   */
  public synchronized void clear()
  {
     cache.clear();
     this.mru     = null;
     this.lru     = null;
  }




  // :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
  // Cache entry

  /**
   * Entry of a double linkedlist to handle the cache objects
   */
  private class LinkedList<L,O>
  {
    public L               key;
    public O               object;
    public LinkedList<L,O> previous;
    public LinkedList<L,O> next;
  }

  // ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
  // Cache provider

  /**
   * Provider of objects to the cache
   */
  public interface Fetcher<K,T> {
	  
    /**
     * Gets an object based on its Identifier
     * @param key Object id
     * @return Keyable Requested object if found; null otherwise
     */
    public T fetch( K key);

    /**
     * Adds a new object to the system
     * @param key Object id
     * @param object Object to add
     */
    public void add(K key, T object);

    /**
     * Updates info of an object 
     * @param key Object id
     * @param object Updated object 
     * @return old object, if found; null otherwise
     */
    public T update(K key, T object);

    /**
     * Removes an object from the provider
     * @param key Object id
     * @param object deleted object, if found; null otherwise
     */
    public T remove( K id);
  }

}
