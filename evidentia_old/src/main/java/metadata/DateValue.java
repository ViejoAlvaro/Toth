package metadata;

import java.time.LocalDateTime;

/**
 * Represents an immutable Date value
 */
public class DateValue extends Value {
	
	
	/**
	 * Builds an instance of a Date Value
	 * @param value the date
	 */
	public DateValue( LocalDateTime value) {
		if (value == null) {
			throw new NullPointerException("Date can not be null");
		}
		
		this.value = value.toString();
	}
	
	
	/**
	 * Returns the DateTime of the value
	 * @return LocalDateTime date time value  
	 */
	public DateValue (String value) {
		try {
			LocalDateTime dateTime = LocalDateTime.parse(value);
			this.value = dateTime.toString();
		}catch( Exception e) {
			throw new IllegalArgumentException ("Value["+ value+ "] is not date");
		}
	}
	

	
	/**
	 *  Returns the DateTime of the value
	 * @return LocalDateTime date time value
	 */
	public  LocalDateTime   getDateTimeValue() {		
		return LocalDateTime.parse(this.value);
	}

}
