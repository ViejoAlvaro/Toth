package security;

/**
 * Represent the access relationship between a subject and an object
 * The interface should be implemented by the subject that wants to have access to the object
 */
public interface Requester {

	/**
	 * Decides if the subject has an adequate security level to access an object
	 * @param objectLevel  Security level of the object
	 * @return true if requester's security level allows access to the object; false otherwise
	 */
	public boolean securityLevelAllowsAccess(TothObject tothObject);

	/**
	 * Decides if the user has enough rights to be allowed to access a data object
	 * @param datum     datum to be accessed
	 * @return true if the requester has enough rights to access the data object; false otherwise
	 */
	public boolean dataRightsAllowAccess( DataObject datum);

	/**
	 * Decides if the user has enough rights to be allowed to perform a procedure
	 * @param proc     Procedure to be performed
	 * @return  true if the requester has enough rights to perform the procedure; false otherwise
	 */
	public boolean performRightsAllowAccess( DataObject proc);

}
