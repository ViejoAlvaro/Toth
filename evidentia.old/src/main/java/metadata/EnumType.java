package metadata;

import java.util.Set;
import java.util.TreeSet;

import util.UTIL;

/**
 * Represents an enumeration Type
 */
public class EnumType implements Comparable<EnumType>{
	
	/*
	 * name	  name of the enumeration
	 * values values in the enumeration
	 */
	private String       name;
	private Set<String>  values;
	
	
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
		values = new TreeSet<String>();
		for( String v: vals) {
			values.add( v.trim().toUpperCase());
		}
	}
	
	/**
	 * Disable null constructor
	 */
	@SuppressWarnings("unused")
	private EnumType() { }
	
	
	
	/** 
	 * Returns the name of the type
	 * @return name of the type
	 */
	public String getName() {
		return this.name;
	}
	
	
	/**
	 * Decides if the presented value is of this type
	 * @param value Value to be checked
	 * @return true if the value presented is of this type; false otherwise
	 */
	public boolean isOfType ( String value) {
		if (value == null)
			return false;
		
		return values.contains(value.trim().toUpperCase());
	}
	

	// ========================================================================================================
	@Override
	public boolean equals(Object o){
		if (this == o)
			return true;

		if (!(o instanceof EnumType ))
			return false;

		EnumType that = (EnumType) o;
		return this.name.equals(that.name) && this.values.equals(that.values);
	}

	@Override
	public int hashCode() { return this.name.hashCode()* 64537; }

	@Override
	public String toString() { 
		StringBuilder s = new StringBuilder();
		s.append("Type{ name["+ name+ "] values[");
		for ( String v: values) {
			s.append(v).append(" ");
		}
		s.append("]}");
		return s.toString();
	}

	@Override
	public int compareTo(EnumType other) { 
		return this.name.compareTo(other.name);
	}

}
