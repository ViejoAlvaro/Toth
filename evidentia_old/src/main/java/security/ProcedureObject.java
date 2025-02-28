package security;

import objects.ProcTag;

/**
 * Represents a guarded procedure object that can only be accessed
 * by specific security levels and access rights
 */

public class ProcedureObject extends DataObject implements ProcTag{

	/**
	 * Builds a new Procedure object
	 * @param securityLevel  security level associated to the object
	 */
	public ProcedureObject(int securityLevel) {
		super(securityLevel);
	}
	
	
	/**
	 * Builds a new Procedure Object
	 * @param id  id of the object
	 * @param securityLevel  security level associated to the object
	 */
	public ProcedureObject( long id, int securityLevel) {
		super(id, securityLevel);
	}
	

	/**
	 * Disable null constructor
	 */
	@SuppressWarnings("unused")
	private ProcedureObject() { 
		super();
	}


	@Override
	public boolean accepts(User user) {

		if (user == null)
			throw new NullPointerException("User that wants access cannot be null");

		if ( !user.securityLevelAllowsAccess(this))
			return false;

		return  user.performRightsAllowAccess(this);
	}

}
