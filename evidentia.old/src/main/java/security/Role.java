package security;

import objects.Parm;
import util.UTIL;

/**
 * Represents a security role
 * A role has a name and associated access rights
 */
public class Role implements Comparable<Role>, Guarded, RoleImporter{

	/*
	 * guard             Security object that protects the role
	 * name              name of the role
	 * dataRights  Rights to data granted to the role 
	 * performRights     Rights to procedures granted to the role
	 */
	private DataObject    guard;
	private String 		  name;
	private Rights        dataRights;
	private Rights  	  performRights;

	/**
	 * Builds a security role
	 * @param name	   Name of the role
	 * @param rights   Access rights
	 */
	public Role (String name,  Rights dataRights, Rights performRights) {

		if ( !UTIL.isValidName(name))
			throw new IllegalArgumentException("Illegal Role name["+ name+ "]");

		if (dataRights == null)
			throw new NullPointerException("Data Access rights of the role can not be null");

		this.name             = name.trim().toLowerCase();
		this.guard            = new DataObject( Parm.ADMIN_SECURITY_LEVEL);
		this.dataRights       = dataRights;
		this.performRights    = performRights;  
	}


	/**
	 * Import the role from an external media
	 * @param importerDirector Role of "Director" of the import Builder
	 */
	public Role( Role.ImporterDirector importerDirector)
	{
		importerDirector.dirija( this);
	}


	/*
	 * Null constructor disabled
	 */
	@SuppressWarnings("unused")
	private Role() { }
	/*
		this.name = "";
		this.guard = new DataObject(Parm.MAX_SECURITY_LEVEL);
		this.dataRights = null;
		this.performRights    = null;
	}
	

	/**
	 * Returns the security guard of the role
	 * @return Security guard of the Role
	 */
	public DataObject getGuard() { return this.guard;}


	/**
	 * Set up the security guard
	 * @param guard guard of the user object
	 */
	@Override
	public void setGuard( DataObject guard) {
		if (guard == null) {
			throw new NullPointerException("User guard can't be null");
		}
		this.guard = guard;
	}


	/** 
	 * Returns the name of the role
	 * @return String role name
	 */
	public String getName() { return this.name;}
	
	
	/**
	 * Updates the name of the role
	 * @param roleName  Name of the role
	 */
	@Override
	public void setName ( String name) {
		if ( !UTIL.isValidName(name)) {
			throw new IllegalArgumentException("Invalid role name["+name+"]" );
		}
		this.name = name.trim().toLowerCase();
	}
	
	
	/**
	 * Determines if the role has provided name
	 * @param name Name of the role to check
	 * @return true if the role has the name; false otherwise
	 */
	public boolean isNamed( String name) { 
		if ( !UTIL.isValidName(name)) {
			return false;
		}
		name = name.trim().toLowerCase();
		return this.name.equals(name);	
	}
	
	
	/**
	 * Set up the data access rights of the role
	 * @param dataRights  data access rights
	 */
	@Override
	public void setDataRights(Rights dataRights) {
		this.dataRights = dataRights;
	}
	
	
	/**
	 * Set up the perform rights of the role
	 * @param performRights  perform rights
	 */
	@Override
	public void setPerformRights(Rights performRights) {
		this.performRights = performRights;
	}


	/**
	 * Grants right of access to a data object
	 * @param anObject rights granted to the data object
	 */
	public void grantDataAccess (DataObject anObject) {
		dataRights.grant(anObject);	
	}


	/**
	 * Revokes all access rights to an object
	 * @param anObject	data object whose access right is revoked
	 */
	public void revokeDataAccess( DataObject anObject) {
		dataRights.revoke(anObject);
	}


	/**
	 * Grants right of performance  to a procedure object
	 * @param procedure  procedure object whose perform 
	 */
	public void grantPerformAccess (DataObject procedure) {
		performRights.grant(procedure);	
	}


	/**
	 * Revokes all access rights to an procedure object
	 * @param anObject	perform object whose access right is revoked
	 */
	public void revokePerformAccess( DataObject procedure) {
		performRights.revoke(procedure);
	}



	/**
	 * Decides if the role has enough rights to be allowed to access an object
	 * @param anObject  Id of the object to be accessed
	 * @return true if dataRights grants access to the object; false otherwise
	 */
	public boolean rightsAllowsAccess( DataObject dataObject) {
		return dataRights.isGrantedAccess(dataObject);
	}



	/**
	 * Decides if the role has enough rights to be allowed to access an object
	 * @param anObject  Id of the object to be accessed
	 * @return true if perform rights grants perform access to the procedure; false otherwise 
	 */
	public boolean rightsAllowsPerform( DataObject procedure) {
		return performRights == null || performRights.isGrantedAccess(procedure);
	}



	// =================================================================================
	/*
	 * Builders to export/import the Role 
	 *  
	 * A builder has two components "Builder" and "Director". 
	 * An export "Builder" knows the external structure of the export media, 
	 * and the  "Director" knows the internal structure of the object. 
	 * Tue "Builder" provides the export methods, the "Director" uses them 
	 */

	/**
	 * Defines a family of export Builders for the Object 
	 */
	public interface Exporter
	{
		/**
		 * Begins the export process
		 */
		public void initExport();

		/**
		 * Export the basic info of the role
		 * @param guard     Security guard of the object    
		 * @param name      role name
		 */
		public void exportBasic( String name, DataObject guard);

		/**
		 * Export the rights of the role
		 * @param dataRights roles rights to data
		 * @param performRights role rights to procedures
		 */
		public void exportRights( Rights dataRights, Rights performRights);

		/**
		 * Ends the export process
		 */
		public void endExport();

		/**
		 * Returns the product of the export operation
		 * @return Object Product of the export 
		 */
		public Object getProduct();

	}// Exporter

	/**
	 * Executes the role of "Director" of the export builder pattern
	 *
	 * @param exporter  Export "Builder" to be directed
	 * @return Object   Export product
	 */
	public Object export( Role.Exporter  exporter)
	{
		exporter.initExport();
		exporter.exportBasic( name, guard);
		exporter.exportRights(dataRights, performRights);
		exporter.endExport();
		return exporter.getProduct();

	}// export


	/**
	 * Defines the role of "Director" of the import builder of the object
	 */
	public interface ImporterDirector
	{
		public void dirija( RoleImporter importer);
	}




	// ========================================================================================================
	@Override
	public boolean accepts( User subject) {
		return guard.accepts( subject);
	}


	// ========================================================================================================
	@Override
	public boolean equals(Object o){
		if (this == o)
			return true;

		if (!(o instanceof Role ))
			return false;

		Role that = (Role) o;
		return this.guard.equals(that.guard) && this.name.equals(that.name);

	}

	@Override
	public int hashCode() { return name.hashCode(); }

	@Override
	public String toString() { 
		return "Role{ name["+ name+ "] "+ guard.toString()+" dataRights#="+ dataRights.getRightsNumber()+ " performRights#="+ performRights.getRightsNumber()+ "}";
	}

	@Override
	public int compareTo(Role that){ return this.equals(that)?  0 : this.name.compareTo(that.name); }

}
