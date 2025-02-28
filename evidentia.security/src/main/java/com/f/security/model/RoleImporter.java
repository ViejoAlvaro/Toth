package com.f.security.model;

import com.f.security.model.object.DataObject;

public interface RoleImporter {

	/**
	 * Set up the security guard
	 * @param guard guard of the user object
	 */
	public void setGuard( DataObject guard);
	
	
	/**
	 * Updates the name of the role
	 * @param roleName  Name of the role
	 */
	public void setName ( String name);
	

	/**
	 * Set up the data access rights of the role
	 * @param dataRights  data access rights
	 */
	public void setDataRights(Rights dataRights);
	
	
	/**
	 * Set up the perform rights of the role
	 * @param performRights  perform rights
	 */
	public void setPerformRights(Rights performRights);

}
