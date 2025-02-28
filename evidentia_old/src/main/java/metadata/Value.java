package metadata;

/**
 * Represents an immutable String value
 */
public class Value implements Comparable<Value>{
	
	/*
	 * value  the value
	 */
	protected String  value;
	
	/**
	 * Builds an instance of a value
	 * @param value value
	 */
	public Value(String value) {
		if (value == null)
			throw new NullPointerException("Value can not be null");
		
		this.value = value.trim();
	}
	
	
	/**
	 * Disable null constructor
	 */
	protected Value() { value = ""; }

	
	/**
	 * Returns the value
	 * @return String value
	 */
	public String getStringValue() {
		return this.value;
	}
	
	


	// ========================================================================================================
	@Override
	public boolean equals(Object o){
		if (this == o)
			return true;

		if (!(o instanceof Value ))
			return false;

		Value that = (Value) o;
		return this.value.equals(that.value);
	}

	@Override
	public int hashCode() { return this.value.hashCode(); }

	@Override
	public String toString() { return "["+ value+ "]";}

	@Override
	public int compareTo(Value other) { 
		return this.value.compareTo(other.value);
	}
	
}
