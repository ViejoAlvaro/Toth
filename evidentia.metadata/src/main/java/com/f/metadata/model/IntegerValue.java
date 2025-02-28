package com.f.metadata.model;

/**
 * Represents an immutable integer value
 */
public class IntegerValue extends Value {
	
	
	/**
	 * Represents an integer Value
	 * @param value the value
	 */
	public IntegerValue( int value) {
		this.value = ""+ value;
	}
	
	
	/**
	 * Represents an integer Value
	 * @param value the value
	 */
	public IntegerValue( long value) {
		this.value = ""+ value;
	}
	
	
	/**
	 * Builds a new integer value
	 * @param value value
	 */
	public IntegerValue ( String value) {
		Long longVal = getNumber(value);
		this.value = longVal.toString();
	}
	

	
	/**
	 * Returns the integer value
	 * @return Integer value
	 */
	public  Long   getIntegerValue() {		
		return getNumber(this.value);
	}
	
	
	/*
	 * Gets the Long number of the integer value
	 * @param value  String representation of the number
	 * @return
	 */
	private Long getNumber( String value) {
		Long intValue=0L;
		try {
			intValue = Long.parseLong(value);
		}catch( Exception e) {
			throw new IllegalArgumentException ("Value["+ value+ "] is not an integer");
		}
		return intValue;
	}

}
