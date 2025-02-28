package com.f.basic.model.cache;


/** 
 * Represents the manager of a cache pattern (Grand 1998)
 */
public class CacheManager<K,T> {    // K= Key type,  T=Cached object type


  /* cache  - Object cache administered by the manager 
   * server - Object provider when it is not found in the cache 
   */
  private Cache<K,T>         cache;
  private Cache.Fetcher<K,T> server;

  /**
   * Gets a new instance of the cache
   * @param server - Object provider when not found in the cache
   * @param size   - Cache size
   */
  public CacheManager(Cache.Fetcher<K,T> server, int size) {
    reset( server, size);
  }


  /**
   * Resets the cache manager
   * @param server - Object provider when not found in the cache
   * @param size   - Cache size
   * @throw NullPointerException when {@code server == null}
   */
  public synchronized void reset(Cache.Fetcher<K,T> server, int size){
    if ( server == null ) {
      throw new NullPointerException("El proveedor de objetos del caché no puede ser nulo");
    }
    this.server = server;
    if ( size < 1 ) {
      this.cache  = new Cache<>( );
    }else {
      this.cache  = new Cache<>( size);
    }
  } 
  

  /**
   * Adds a new object to the cache, if it is not already there
   * @param key    Id of the object to be added
   * @param object Object to add
   */
  public synchronized void add(K key, T object){
	if (cache.fetch(key) == null){
	  cache.add (key, object);
      server.add(key, object);
	}
  } 
  

  /**
   * Updates an object entry in the cache
   * @param key    Id of the object to update
   * @param object Object that updates the existing copy
   */
  public synchronized T update( K key, T object){
    server.update(key, object);
    return cache.replace(key, object);
  } 
  

  /**
   * Gets an object by id
   * @param key Object id
   * @return Requested object, if found, null if not found
   */
  public synchronized T fetch( K key){
    T theObject = cache.fetch(key);
    if ( theObject == null ){
      theObject = server.fetch(key);
      if ( theObject != null ) {
        cache.add(key, theObject);
      }
    }
    return theObject;
  }
  

  /**
   * Deletes the object from the cache and the server
   * @param  key Object id
   * @return Deleted object, if found; null if not found
   */
  public synchronized T remove( K key){
    server.remove( key);
    return cache.remove( key);
  }
  

  /**
   * Deletes an object from the cache. If needed it has to be loaded again
   * @param key Object id
   * @return Deleted object, if found; null if not found
   */
  public synchronized T invalidate( K key){
    return cache.remove(key);
  }
  

  /**
   * Returns the string representation of the object
   * @return String representing the object
   */
  public synchronized String toString(){
         return cache.toString()+ server.toString();
  }


}
