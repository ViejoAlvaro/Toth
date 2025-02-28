package com.f.metadata.model;

import com.f.basic.model.Parm;
import com.f.basic.model.UTIL;

/**
 * Represents a metadata field descriptor
 */
public class Field implements Comparable<Field>{
	
	/*
	 * name	 Name of the field
	 * type  Type of the field
	 */
	protected final String    name;
	protected final Parm.TYPE type;

	/**
	 * Builds a new Field
	 * @param name  Field name
	 * @param type  Field type
	 */
	public Field( String name, Parm.TYPE type) {
		if ( ! UTIL.isValidName(name))
			throw new IllegalArgumentException("Illegal variable name["+ name+ "]");
		
		if ( type == null )
			throw new IllegalArgumentException("Field type value can't be null");
		
		this.name = name.trim().toLowerCase();
		this.type = type;
	}
	
	/**
	 * Null constructor dummy
	 */
	protected Field() { 
		this.name = "FIELD1";
		this.type = Parm.TYPE.STRING;
	}
	
	/**
	 * Returns the name of the field
	 * @return	String name of the field
	 */
	public String  getName() {
		return this.name;
	}
	
	/**
	 * Returns the type of the field
	 * @return  String type of the field
	 */
	public Parm.TYPE getType(){
		return this.type;
	}

	


	// ========================================================================================================
	@Override
	public boolean equals(Object o){
		if (this == o)
			return true;

		if (!(o instanceof Field ))
			return false;

		Field that = (Field) o;
		return this.name.equals(that.name) && this.type.equals(that.type);
	}

	@Override
	public int hashCode() { 
		return this.name.hashCode()*655347+ this.type.hashCode()*32767; 
	}

	@Override
	public String toString() { 
		return "Field{ name["+ name+ "] type["+ type+ "]}";
	}

	@Override
	public int compareTo(Field other) { 
		String thisV  = this.name+  this.type.toString();
		String otherV = other.name+ other.type.toString();
		return thisV.compareTo(otherV);
	}

}
