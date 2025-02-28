package security;

import java.security.Principal;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import javax.security.auth.Subject;

import metadata.Metadata;
import objects.Parm;
import util.UTIL;


/**
 * Represents someone that wants to use the system
 */
public class User implements Principal, Guarded, Requester, Comparable<User>, UserImporter{

	/*
	 * guard          Security object that protects the role
	 * userId         User id
	 * firstName      First name
	 * lastName       Last name
	 * email          email
	 * requiredRoles  Set of required roles assigned to user
	 * enablingRoles  Set of enabling roles assigned to the user
	 * metadata       Metadata associated to the user  (can be null)
	 */
	private DataObject  guard;
	private String      userId;
	private String      firstName;
	private String      lastName;
	private String      email;
	private Set<Role>   requiredRoles;
	private Set<Role>   enablingRoles;
	private Metadata    metadata;

	/**
	 * Builds a new user
	 * @param userId         User id
	 * @param firstName      User's first name
	 * @param lastName       User's last name
	 * @param email          User's email
	 * @param requiredRoles  Required Roles assigned to the user 
	 * @param enablingRoles  Roles that enable execution/access 
	 */
	public User (String userId, String firstName, String lastName, String email, int securityLevel, Metadata metadata, Set<Role> requiredRoles, Set<Role> enablingRoles) {

		this.guard         = new DataObject( securityLevel);
		setBasic( userId, firstName, lastName, email);
		setMetadata(metadata);
		this.requiredRoles = new TreeSet<Role>();
		this.enablingRoles = new TreeSet<Role>();

		if (requiredRoles != null) {
			this.requiredRoles.addAll(requiredRoles);
		}

		if (enablingRoles != null) {
			this.enablingRoles.addAll(enablingRoles);
		}

	}


	/**
	 * Import the user from an external media
	 * @param importerDirector Role of Director of the import Builder
	 */
	public User( User.ImporterDirector importerDirector)
	{
		this.requiredRoles = new TreeSet<Role>();
		this.enablingRoles = new TreeSet<Role>();
		importerDirector.dirija( this);
	}




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
	 * Set up the basic user information
	 * @param userId   User code
	 * @param firstName First name
	 * @param lastName  Last name
	 * @param email     Email address
	 */
	@Override
	public void setBasic( String userId, String  firstName, String lastName, String  email) {

		if ( userId == null  || userId.length() == 0 || userId.trim().length() == 0)
			throw new IllegalArgumentException( "Invalid user id["+ userId+ "]");

		if ( ! UTIL.isValidName(firstName))
			throw new IllegalArgumentException("Invalid User firstName["+ firstName+ "]");

		if ( ! UTIL.isValidName(lastName))
			throw new IllegalArgumentException("Invalid User lastName["+ lastName+ "]");

		if ( email == null || email.length() == 0 || email.trim().length() == 0)
			throw new IllegalArgumentException("Invalid email["+ email+ "]");

		this.userId        = userId;
		this.firstName     = firstName.trim();
		this.lastName      = lastName.trim();
		this.email         = email;
	}


	/*
	 * Null constructor disabled
	 */
	@SuppressWarnings("unused")
	private User() { }


	/**
	 * Returns the security guard of the user
	 * @return Security guard of the User
	 */
	public DataObject getGuard() { 
		return this.guard;
	}


	/**
	 * Returns the security level of the user
	 * @return int security level of the User
	 */
	public int getSecurityLevel() { 
		return this.guard.getSecurityLevel();
	}


	/**
	 * Decides if the user object is public
	 * @return  true if the user object is public; false otherwise
	 */
	public boolean isPublic() {
		return this.guard.isPublic();
	}


	/**
	 * Returns requested required role given a role name
	 * @param   roleName name of requested role
	 * @return Role  the requested role; null if user is not granted that role
	 */
	public Role   getRequiredRole( String roleName) {
		for (Role r: requiredRoles) {
			if ( r.isNamed(roleName)) {
				return r;
			}
		}
		return null;
	}


	/**
	 * Returns requested required role given a role name
	 * @param   roleName name of requested role
	 * @return Role  the requested role; null if user is not granted that role
	 */
	public Role   getEnablingRole( String roleName) {
		for (Role r: enablingRoles) {
			if ( r.isNamed(roleName)) {
				return r;
			}
		}
		return null;
	}


	/**
	 * Returns the security level of the user
	 * @param int new security level of the User
	 */
	public void setSecurityLevel(int securityLevel) { 
		if ( securityLevel < Parm.MIN_SECURITY_LEVEL || securityLevel > Parm.MAX_SECURITY_LEVEL) {
			throw new IllegalArgumentException("Invalid security level["+ securityLevel+"]");
		}
		this.guard.setSecurityLevel(securityLevel);
	}


	/**
	 * Returns the metadata associated with the user
	 * @return  metadata associated to the user
	 */
	public Metadata getMetadata() {
		return this.metadata;
	}


	/**
	 * Set up the user metadata 
	 * @param metadata  user's metadata. Can be null
	 */
	@Override
	public void setMetadata(Metadata metadata) {
		this.metadata = metadata; 
	}


	/**
	 * Assigns a required role to the user
	 * @param role Role to be assigned
	 */
	@Override
	public void addRequiredRole( Role role) {
		if (role != null) {
			requiredRoles.add(role);
		}
	}


	/**
	 * Iterator to the required roles
	 * @return Iterator<Role> iterator to the required roles
	 */
	public Iterator<Role> requiredRolesIterator(){
		return requiredRoles.iterator();
	}


	/**
	 * Assigns an enabling role to the user
	 * @param role Role to be assigned
	 */
	public void addEnablingRole( Role role) {
		if (role != null)
			enablingRoles.add(role);
	}


	/**
	 * Removes a required role assigned to the user
	 * @param roleName name of the role to be removed
	 */
	public void removeRequiredRole( String roleName) {
		Role r = getRequiredRole(roleName);
		if (r != null) {
			requiredRoles.remove(r);
		}
	}


	/**
	 * Iterator to th enabling roles
	 * @return Iterator<Role>  iterator to the enabling roles
	 */
	public Iterator<Role> enablingRolesIterator(){
		return enablingRoles.iterator();
	}



	/**
	 * Removes an enabling role assigned to the user
	 * @param roleName name of the role to be removed
	 */
	public void removeEnablingRole( String roleName) {
		Role r = getEnablingRole(roleName);
		if (r != null) {
			enablingRoles.remove(r);
		}
	}



	// =================================================================================
	/*
	 * Builders to export/import the User 
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
		 * Export the basic info of the User
		 * @param guard     Security guard of the object    
		 * @param userId    User id
		 * @param firstName User's first name 
		 * @param lastName  User's last name 
		 * @param email     User's email
		 */
		public void exportBasic( TothObject guard, String userId, String firstName, String lastName, String email);

		/**
		 * Export the required roles of the user
		 * @param requiredRoles Iterator<Role> to the required Roles
		 */
		public void exportRequiredRoles( Iterator<Role> requiredRoles);

		/**
		 * Export the enabling roles of the user
		 * @param enablingRoles Iterator<Role> Iterator to enabling Roles
		 */
		public void exportEnablingRoles( Iterator<Role> enablingRoles);


		/**
		 * Exports the metadata associated to the user
		 * @param metadata Metadata associated to the user
		 */
		public void exportMetadata( Metadata metadata);

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
	public Object export( User.Exporter  exporter)
	{
		exporter.initExport();
		exporter.exportBasic( guard, userId, firstName, lastName, email);
		exporter.exportRequiredRoles(requiredRoles.iterator());
		exporter.exportEnablingRoles(enablingRoles.iterator());
		exporter.exportMetadata(metadata);
		exporter.endExport();
		return exporter.getProduct();

	}// export


	public interface ImporterDirector
	{
		public void dirija( UserImporter importer);
	}



	// ========================================================================================================
	@Override
	public boolean accepts(User subject) {
		return  guard.checkSecurityLevel(subject.getSecurityLevel()) && subject.dataRightsAllowAccess(guard);
	}



	// ========================================================================================================

	/**
	 * Gets the full name of the user
	 * @return String full name of the user
	 */
	@Override
	public String getName() { return lastName + " "+ firstName;}

	/** 
	 * Returns true if the specified subject is implied by this principal
	 * @return true as the Subject is implied by this principal
	 */
	@Override
	public boolean implies(Subject subject) { 
		return true;   //TODO: Check implies (subject)
	}

	// ======================================================================================================== 
	//  Implements Requester


	/**
	 * Decides if the subject has an adequate security level to access an object
	 * @param objectLevel  Security level of the object
	 * @return true if requester's security level allows access to the object; false otherwise
	 */
	@Override
	public boolean securityLevelAllowsAccess(TothObject anObject) {
		return  anObject.checkSecurityLevel(getSecurityLevel());
	}


	/**
	 * Decides if the user has enough rights to be allowed to access a data object
	 * @param datum     datum to be accessed
	 * @return true if the requester has enough rights to access the data object; false otherwise
	 */
	@Override
	public boolean dataRightsAllowAccess( DataObject datum) {

		/*
		 * [1] User must have an adequate security level
		 * [2] All required roles must grant data access
		 * [3] At least one of the enabling roles should grant data access
		 */

		if (!securityLevelAllowsAccess(datum)) {
			return false;
		}

		for ( Role r : requiredRoles) {
			if ( !r.rightsAllowsAccess(datum)) {
				return false;
			}
		}

		for ( Role e : enablingRoles) {
			if ( e.rightsAllowsAccess(datum)) {
				return true;
			}
		}
		return false;
	}



	/**
	 * Decides if the user has enough rights to be allowed to perform a procedure
	 * @param proc Procedure to be performed
	 * @return  true if the requester has enough rights to perform the procedure; false otherwise
	 */
	@Override
	public boolean performRightsAllowAccess( DataObject proc) {

		/*
		 * [1] User must have an adequate security level
		 * [2] All required roles must grant perform action
		 * [3] At least one of the enabling roles should grant perform action
		 */
		if (!securityLevelAllowsAccess((TothObject)proc)) {
			return false;
		}

		for ( Role r : requiredRoles) {
			if ( !r.rightsAllowsPerform((DataObject)proc)) {
				return false;
			}
		}

		for ( Role e : enablingRoles) {
			if ( e.rightsAllowsPerform(proc)) {
				return true;
			}
		}
		return false;
	}

	// ========================================================================================================
	@Override
	public boolean equals(Object o){
		if (this == o)
			return true;

		if (!(o instanceof User ))
			return false;

		User that = (User) o;
		return this.guard.equals(that.guard) && this.userId.equals(that.userId);
	}

	@Override
	public int hashCode() { return Objects.hash(firstName, lastName, email); }

	@Override
	public String toString() { 
		StringBuilder s = new StringBuilder(60);
		s.append("User{")
		.append("id["   + userId+ "] ")
		.append( guard.toString())
		.append(" first name[" + firstName+ "]")
		.append(" last name[" + firstName+ "]")
		.append(" email["+ email+ "]")
		.append(" access level["+ guard.getSecurityLevel()+ "] ")
		.append(" required roles[");
		for (Role r: requiredRoles) {
			s.append(r.getName()+ " ");          
		}
		s.append("] enablingRoles[");
		for (Role e: enablingRoles) {
			s.append(" ").append(e.getName());
		}
		s.append("] ").append(metadata.toString()).append("}");

		return s.toString();
	}

	@Override
	public int compareTo(User that){ 
		String u1 = this.lastName+ " "+ this.firstName+ " "+ this.email;
		String u2 = that.lastName+ " "+ that.firstName+ " "+ that.email;
		return u1.compareTo(u2);
	}


}
