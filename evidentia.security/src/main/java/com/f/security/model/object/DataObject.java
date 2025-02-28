package com.f.security.model.object;

import com.f.security.model.User;
import com.f.security.model.classification.Component;

/**
 * Represents a guarded data object that can only be accessed
 * by specific security levels and access rights
 */
public class DataObject extends TothObject implements Component {

	/**
	 * Builds a new Data Object object
	 * @param securityLevel  security level associated to the object
	 */
	public DataObject(int securityLevel) {
		super(securityLevel);
	}
	
	/**
	 * Builds a new Data Object
	 * @param id  id of the object
	 * @param securityLevel  security level associated to the object
	 */
	public DataObject( long id, int securityLevel) {
		super(id, securityLevel);
	}

	/**
	 * Disable null constructor
	 */
	protected DataObject() { 
		super();
	}


	/**
	 * Returns the security guard of the object
	 * @return   security guard of the object
	 */
	@Override
	public DataObject   getGuard() {
		return this;
	}

	/**
	 * Decides if the object accepts access by a user
	 * @param subject   User that wants access to this object
	 * @return  boolean if access is granted; false otherwise
	 */
	public boolean accepts (User user) {

		if (user == null)
			throw new NullPointerException("User that wants access cannot be null");

		if ( !user.securityLevelAllowsAccess(this))
			return false;

		return  user.dataRightsAllowAccess(this);
	}

}
