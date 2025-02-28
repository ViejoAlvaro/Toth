package com.f.metadata.model;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.f.basic.model.UTIL;


/**
 * Represents an enumeration Type
 */
public class EnumType implements Comparable<EnumType>{

	/*
	 * name	   name of the enumeration
	 * entries values in the enumeration
	 */
	private String       name;
	private Map<String, Entry> entries;

	public  Entry MD = new Entry("MD", "Missing Data",   -999);
	public  Entry NA = new Entry("NA", "Not applicable", -998);
	public  Entry IN = new Entry("IN", "Inconsistent",   -997);


	/**
	 * Represents an enumeration type
	 * @param name  Name of the enumeration
	 * @param vals  Values of the enumeration
	 */
	public EnumType ( String name, Set<String> vals) {
		if ( !UTIL.isValidName(name)) {
			throw new IllegalArgumentException("Illegal name["+ name+ "]");
		}

		if (vals == null) {
			throw new NullPointerException("Values of the type can't be null");
		}

		this.name = name.trim().toUpperCase();
		setupEntries();
		int i = 1;
		for( String v: vals) {
			addEntry( v, v, i++);
		}
	}



	/**
	 * Represents an enumeration type
	 * @param name  Name of the enumeration
	 * @param vals  Entriy values of the enumeration
	 */
	public EnumType (String name) {
		if ( !UTIL.isValidName(name)) {
			throw new IllegalArgumentException("Illegal name["+ name+ "]");
		}
		this.name = name;
		setupEntries();
	}
	
	
	private void setupEntries() {
		entries = new TreeMap<String, Entry>();
		entries.put( MD.getKey(), MD);
		entries.put( NA.getKey(), NA);
		entries.put( IN.getKey(), IN);		
	}



	/** 
	 * Returns the name of the type
	 * @return name of the type
	 */
	public String getName() {
		return this.name;
	}
	
	
	/**
	 * Returns the key associated to a value
	 * @param key Value key
	 * @return String  key
	 */
	public String getKey(String key) {
		Entry entry = getEntry(key);
		return entry == null? null: entry.getKey();
	}
	
	
	/**
	 * Returns the description associated to a value
	 * @param key  Value key
	 * @return String Description associated to the value
	 */
	public String getDescription(String key) {
		Entry entry = getEntry(key);
		return entry == null? null: entry.getDescription();
	}
	
	/**
	 * Gets the ordinal associated to a value
	 * @param key Value key
	 * @return  int  Ordinal associated to the value
	 */
	public Integer getOrdinal(String key) {
		Entry entry = getEntry(key);
		return entry == null? null: entry.getOrdinal();
	}
	
	/*
	 * Gets the entry associated to a key 
	 * @param key  Value key
	 * @return Entry associated to the value
	 */
	private Entry getEntry( String key) {
		if (key == null) {
			return null;
		}
		key = key.trim();
		Entry entry = entries.get(key);
		return entry;
		
	}
	
	
	/**
	 * Adds a new entry value to the type
	 * @param key	Value key
	 * @param description Description associated to the value
	 * @param ordinal Ordinal associated to the value
	 * @return  true if the value was added to the type; false otherwise
	 */
	public boolean addEntry( String key, String description, int ordinal) {
		if ( key == null || key.trim().length()== 0) {
			throw new IllegalArgumentException("Illegal entry key["+ key+ "]");
		}
		if ( entries.get(key) != null) {
			return false;
		}
		if (description == null || description.trim().length() == 0) {
			description = key;
		}
		entries.put(key,  new Entry( key, description, ordinal));
		return true;
	}


	/**
	 * Decides if the presented value is of this type
	 * @param value Value to be checked
	 * @return true if the value presented is of this type; false otherwise
	 */
	public boolean isOfType ( String value) {
		if (value == null) {
			return false;
		}
		String key = value.trim().toUpperCase();
		Entry  val = entries.get(key);
		return val != null;
	}


	// ========================================================================================================
	// An enumeration entry
	private class Entry {

		/*
		 * key			Key of the enumeration entry
		 * description  Text description of the entry
		 * ordinal		integer value of the entry
		 * MD           Missing Data value
		 * NA           Not applicable value
		 * IN           Inconsistent or invalid value 
		 *
		 */
		private String  key;
		private String  description;
		private Integer ordinal;

		public Entry( String key, String description, int ordinal) {
			if (key == null) {
				throw new NullPointerException("Key of the enumeration value can't be null");
			}
			if (description == null) {
				description = key;
			}
			this.key         = key;
			this.description = description;
			this.ordinal     = ordinal;
		}
		
		public String getKey() {
			return this.key;
		}
		
		public String getDescription() {
			return this.description;
		}
		
		public Integer getOrdinal() {
			return this.ordinal;
		}

		public boolean equals(Object o) {
			if (this == o)
				return true;

			if (!(o instanceof EnumType.Entry ))
				return false;

			Entry that = (Entry) o;
			return this.key.equals(that.key);
		}
		
		public int hashCode() {
			return this.key.hashCode()* 32767 + this.ordinal*3;
		}
		
		public String toString() {
			return "{key["+ this.key+ "] ordinal["+ this.ordinal+ "] description["+ this.description+ "]}";
		}
		
	}

	// ========================================================================================================
	@Override
	public boolean equals(Object o){
		if (this == o)
			return true;

		if (!(o instanceof EnumType ))
			return false;

		EnumType that = (EnumType) o;
		return this.name.equals(that.name) && this.entries.equals(that.entries);
	}

	@Override
	public int hashCode() { return this.name.hashCode()* 64537; }

	@Override
	public String toString() { 
		StringBuilder s = new StringBuilder();
		s.append("Type{ name["+ name+ "] values[\n");
		for ( String v: entries.keySet()) {
			Entry e = entries.get(v);
			s.append(e.getKey()).append(" ")
			 .append(e.getOrdinal()).append(" ")
			 .append(e.getDescription()).append("\n");
		}
		s.append("]}");
		return s.toString();
	}

	@Override
	public int compareTo(EnumType other) { 
		return this.name.compareTo(other.name);
	}

}
