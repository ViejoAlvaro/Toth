package objects;

import security.DataObject;
import security.User;

public interface Component {

	/**
	 * Returns the security guard
	 * @return security guard of the component
	 */
	public DataObject   getGuard();

	/**
	 * Returns the id of the object
	 * @return Object's id
	 */
	public long   getId();


	/**
	 * Returns the security  level associated to the object
	 * @return Object's security level
	 */
	public int getSecurityLevel();	


	/**
	 * Resets the security level associated to the subject
	 * @param securityLevel New security level
	 */
	public void setSecurityLevel (int securityLevel);


	/**
	 * Checks if presented security level allows access to the object
	 * @param securityLevel  level of user who wants access to the object
	 * @return  true if the user level allows access to the object; false otherwise
	 */
	public boolean checkSecurityLevel( int userLevel);


	/**
	 * Decides if the object can be accessed by a user with a role
	 * @param user   User that wants access to this object
	 * @param role   Role used by the user who wants access to the component
	 * @return  boolean if access is granted; false otherwise
	 */
	public boolean accepts (User user);

}
