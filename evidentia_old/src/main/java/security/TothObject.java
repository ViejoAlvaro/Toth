package security;

import objects.Identity;
import objects.Parm;


/**
 * Represents a guarded object that requires protection
 */
public abstract class TothObject implements Comparable<TothObject> {

	/*
	 * objectId			Unique id of the object within the system
	 * securityLevel	Security level of the object
	 */
	protected final long  objectId;
	protected int 		  securityLevel;

	/**
	 * Builds a new Toth object
	 * @param securityLevel  security level associated to the object
	 */
	public TothObject(int securityLevel) {	
		if ( securityLevel < Parm.MIN_SECURITY_LEVEL || securityLevel > Parm.MAX_SECURITY_LEVEL) {
			throw new IllegalArgumentException ("Security level is invalid["+ securityLevel+ "]");
		}
		this.objectId 	   = Identity.assignId();
		this.securityLevel = securityLevel;
	}
	
	/**
	 * Builds a specificTothObject
	 * @param id    Object's id
	 * @param securityLevel Object's security level
	 */
	public TothObject( long id, int securityLevel) {
		if ( securityLevel < Parm.MIN_SECURITY_LEVEL || securityLevel > Parm.MAX_SECURITY_LEVEL) {
			throw new IllegalArgumentException ("Security level is invalid["+ securityLevel+ "]");
		}
		this.objectId = id;
		this.securityLevel = securityLevel;
	}
	

	/**
	 * Disable null constructor
	 */
	protected TothObject() {
		this.objectId      = Parm.NULL_ID;
		this.securityLevel = Parm.MAX_SECURITY_LEVEL;
	}


	/**
	 * Returns the id of the object
	 * @return Object's id
	 */
	public long   getId() {
		return objectId;
	}


	/**
	 * Returns the security  level associated to the object
	 * @return Object's security level
	 */
	public int getSecurityLevel() {
		return securityLevel;
	}



	/**
	 * Resets the security level associated to the subject
	 * @param securityLevel New security level
	 */
	public void setSecurityLevel (int securityLevel) {

		if( securityLevel < 0 || securityLevel > Parm.MAX_SECURITY_LEVEL)
			throw new IllegalArgumentException("Invalid security level["+ securityLevel+ "]");

		this.securityLevel = securityLevel;
	}



	/**
	 * Decides if the item is public
	 * @return true if the object is public; false otherwise
	 */
	public boolean isPublic() {
		return this.securityLevel == Parm.PUBLIC_SECURITY_LEVEL;
	}


	/**
	 * Checks if presented security level allows access to the object
	 * @param securityLevel  level of user who wants access to the object
	 * @return  true if the user level allows access to the object; false otherwise
	 */
	public boolean checkSecurityLevel( int userLevel) {
		return   userLevel >= this.securityLevel;
	}


	/**
	 * Decides if the object accepts access by a user
	 * @param subject   User that wants access to this object
	 * @return  boolean if access is granted; false otherwise
	 */
	public abstract boolean accepts (User user);
	/*
	 {
		if (user == null)
			throw new NullPointerException("User that wants access cannot be null");

		if ( !user.securityLevelAllowsAccess(this))
			return false;

		return  user.dataRightsAllowAccess(this);
	}
	 */



	// =================================================================================
	/*
	 * Builders to export the Object 
	 *  
	 * A builder has two components "Builder" and "Director". 
	 * An export "Builder" knows the external structure of the export media, 
	 * and the "Director" knows the internal structure of the object. 
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
		 * Exports the object fields
		 * @param objectId   Object identification
		 * @param securityLevel security level of the object
		 */
		public void exportObject( long  objectId, int securityLevel);

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
	public Object export( TothObject.Exporter  exporter)
	{
		exporter.initExport();
		exporter.exportObject( objectId, securityLevel);
		exporter.endExport();
		return exporter.getProduct();
	}// export




	// ========================================================================================================
	@Override
	public boolean equals(Object o){
		if (this == o)
			return true;

		if (!(o instanceof TothObject ))
			return false;

		TothObject that = (TothObject) o;
		return this.objectId == that.objectId;

	}

	@Override
	public int hashCode() { return (int)objectId * 32767; }

	@Override
	public String toString() { return "guard{ id["+ objectId+ "] level["+ securityLevel+ "]}";}

	@Override
	public int compareTo(TothObject other) { 
		String thisId  = ""+ this.objectId;
		String otherId = ""+ other.objectId;
		return thisId.compareTo(otherId);
	}


}
