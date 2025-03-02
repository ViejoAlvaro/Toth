package metadata;

/**
 * Represents an immutable boolean value
 */
public class BooleanValue extends Value{
	
	
	/**
	 * Represents a boolean Value
	 * @param value the value
	 */
	public BooleanValue( boolean value) {
		this.value =  value? "TRUE": "FALSE";
	}
	
	
	/**
	 *Builds a new Boolean value
	 * @param value value
	 */
	public BooleanValue ( String value) {
		Boolean boolVal = convertToBoolean( value);
		this.value = boolVal.toString();
	}
	

	
	/**
	 * Returns the boolean value
	 * @return Boolean value
	 */
	public  Boolean   getBooleanValue() {		
		return convertToBoolean(this.value);
	}
	
	
	/*
	 * Converts the string value to a boolean
	 * @param value String value
	 * @return Boolean converted value
	 */
	private Boolean convertToBoolean( String value) {
		Boolean boolVal;
		try {
			boolVal = Boolean.parseBoolean(value);
			this.value = boolVal.toString();
		}catch( Exception e) {
			throw new IllegalArgumentException ("Value["+ value+ "] is not boolean");
		}
		return boolVal;
		
	}

}
