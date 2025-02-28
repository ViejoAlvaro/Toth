package security;

/**
 * Describes a security guard of a resource
 */
public interface Guarded {

	/**
	 * Checks if the object can be accessed by a user
	 * @param subject Subject that wants to access the object 
	 * @return true if the object can be accessed / false otherwise
	 */
	public boolean accepts( User subject);

}
