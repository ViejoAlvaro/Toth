package metadata;

import objects.Parm;
import util.UTIL;

public class EnumField extends Field {
	
	/*
	 * enumTypeName	 Name of the field enum type 
	 */
	private String enumTypeName;
	
	/**
	 * Builds a new field of type enum
	 * @param name	Name of the field
	 * @param enumTypeName  Name of the enumeration that defines the field type
	 */
	public EnumField(String name, String enumTypeName) {
		super(name, Parm.TYPE.LIST);
		if ( ! UTIL.isValidName(name)) { 
			throw new IllegalArgumentException("Invalid field name["+ name+ "]");
		}
		EnumRegistry registry = EnumRegistry.getInstance();
		EnumType     enumType = registry.getType(enumTypeName);
		if (enumType == null) { 
			throw new IllegalArgumentException("Enum type name["+ enumTypeName+ "] is not registered");
		}
		this.enumTypeName = enumTypeName;
	}
	
	
	/**
	 * Returns the enum name that defines the field type
	 * @return Enum type name
	 */
	public String enumTypeName() {
		return this.enumTypeName;
	}
	
	
	/**
	 * Returns the Enum type of the field
	 * @return enum type of the field
	 */
	public EnumType  getEnumType() {
		return EnumRegistry.getInstance().getType(this.enumTypeName);	
	}
	
    


	// ========================================================================================================
	@Override
	public boolean equals(Object o){
		if (this == o)
			return true;

		if (!(o instanceof EnumField ))
			return false;

		EnumField that = (EnumField) o;
		return this.name.equals(that.name) && this.type.equals(that.type) && this.enumTypeName.equals(that.enumTypeName);
	}

	@Override
	public int hashCode() { 
		return this.name.hashCode()*32767+ this.type.hashCode()*3 + this.enumTypeName.hashCode()*65337; 
	}

	@Override
	public String toString() { 
		return "EnumField{ name["+ name+ "] type["+ type+ "] enumTypeName["+ enumTypeName+ "]}";
	}


}
