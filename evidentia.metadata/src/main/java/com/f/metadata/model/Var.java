package com.f.metadata.model;

public final class Var implements Comparable<Var>{
	
	/*
	 * field	field descriptor the variable
	 * value    Initial value of the variable
	 */
	private final Field field;
	private String value;

	
	/**
	 * Builds a new variable
	 * @param field	 field descriptor the variable
	 * @param value  Initial value of the variable
	 */
	public Var(Field field, String value) {

		if ( field == null )
			throw new NullPointerException("Variable field descriptor can not be null");
		
		if ( value == null )
			throw new IllegalArgumentException("Variable value can't be null");
		
		this.field =  field;
		this.value = value;
	}
	
	
	/** 
	 * Returns the field descriptor of the variable
	 * @return Field  descriptor of the variable
	 */
	public Field getField() {
		return this.field;
	}
	
	
	/**
	 * Sets the value of the variable
	 * @param value  New value of the variable
	 */
	public void  setValue( String value) {
		
		if ( value == null )
			throw new IllegalArgumentException("Variable value can't be null");
		
		this.value = value;
	}
	
	/**
	 * Returns the value of the variable
	 * @return String value of the variable
	 */
	public String getValue() {
		return this.value;
	}


	// ========================================================================================================
	@Override
	public boolean equals(Object o){
		if (this == o)
			return true;

		if (!(o instanceof Var ))
			return false;

		Var that = (Var) o;
		return this.field.equals(that.field) && this.value.equals(that.value);
	}

	@Override
	public int hashCode() { return this.field.hashCode()*32767+ this.value.hashCode()*655347; }

	@Override
	public String toString() { return "Var {"+ field.toString()+ "="+ value+ "}";}

	@Override
	public int compareTo(Var other) { 
		String thisV  = this.field.getName()+  this.value;
		String otherV = other.field.getName()+ other.value;
		return thisV.compareTo(otherV);
	}

}
