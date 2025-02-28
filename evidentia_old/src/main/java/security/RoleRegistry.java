package security;

import java.util.Iterator;
import java.util.TreeMap;

/**
 * Represents the registry of all roles
 */
public class RoleRegistry {
	
	/*
	 * theRoles<RoleName, Role>  Registered Roles
	 */
	private TreeMap<String, Role>  theRoles;

    /**
     * Null constructor can only be called internally
     */
    private RoleRegistry() { 
    	theRoles = new TreeMap<String, Role>();
    }

    /*
     *  The inner static class responsible for holding the RoleRegistry Singleton instance
     */
    private static class RegistryHelper {
    	
        //  The final instance of the Roles Singleton class
        private static final RoleRegistry INSTANCE = new RoleRegistry();
    }

    /**
     * Returns the single instance of the RoleRegistry
     * @return RoleRegistry Single instance of RoleRegistry
     */
    public static RoleRegistry getInstance() {
        return RegistryHelper.INSTANCE;
    }
    
    
    /**
     * Clean up the register
     * (Use only for testing purposes)
     */
    public void clear() {
    	theRoles.clear();
    }

    /**
     * Register a new Role
     * @param role  Type to be registered
     * @return true if the role was registered; false if role to register is null 
     *              or there was a role with the same name already registered
     */
    public  boolean addRole( Role role) {
    	if (role == null) {
    		throw new NullPointerException("Role to register can't be null");
    	}
    	
    	String roleName = role.getName();   	
    	if (theRoles.containsKey(roleName)) {
    		return false;
    	}
    	
    	theRoles.put(roleName, role);
    	return true;
    }
    
    
    /**
     * Returns a registered Role
     * @param roleName Name of the Role to get
     * @return Role the requested role
     */
    public Role getRole( String roleName) {
    	return theRoles.get(roleName);
    }
    


	// ========================================================================================================
	@Override
	public boolean equals(Object o){
		if (this == o)
			return true;

		if (!(o instanceof RoleRegistry ))
			return false;

		RoleRegistry that = (RoleRegistry) o;
		return this.theRoles.equals(that.theRoles);
	}

	@Override
	public int hashCode() { return this.theRoles.hashCode(); }

	@Override
	public String toString() { 
		StringBuilder s = new StringBuilder();
		s.append("RoleRegistry{"); 
		Iterator<String> keyIterator = theRoles.keySet().iterator();
		while (keyIterator.hasNext()) {
			String RoleName = keyIterator.next();
			s.append(RoleName).append(" ");
		}
		s.append("}");
		return s.toString();	
	}


}
