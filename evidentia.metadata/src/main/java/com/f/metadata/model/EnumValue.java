package com.f.metadata.model;

import com.f.basic.model.UTIL;

/**
 * Represents a value belonging to an enumeration
 */
public class EnumValue extends Value {
	
	/*
	 * enumType	  Enumeration restricting the options of the value
	 */
	private String enumTypeName;
	
	/**
	 * Builds a new EnumValue
	 * @param enumType
	 * @param value
	 */
	public EnumValue( String enumTypeName, String value) {
		super(value);
		if ( !UTIL.isValidName(enumTypeName)) {
			throw new IllegalArgumentException("Name of the EnumType is invalid["+ enumTypeName+ "]");
		}
		
		this.enumTypeName = enumTypeName.trim().toUpperCase();
		EnumType  theType = EnumRegistry.getInstance().getType(this.enumTypeName);
		if ( theType == null) {
			throw new IllegalArgumentException("EnumType does not exist in enum type registry["+ enumTypeName+ "]");
		}
		
		if ( !theType.isOfType( value)) {
			throw new IllegalArgumentException("Value ["+ value+ "] does not belong to ["+ enumTypeName+ "]");		
		}		
	}

}
