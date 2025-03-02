package metadata;

import java.util.Iterator;
import java.util.TreeMap;

/**
 * Represents the unique Registry of Enumeration Types
 */
public class TypeRegistry {
	
	/*
	 * theTypes<typeName, type>  Registered types
	 */
	private TreeMap<String, EnumType>  theTypes;

    /**
     * Null constructor can only be called internally
     */
    private TypeRegistry() { 
    	theTypes = new TreeMap<String, EnumType>();
    }

    /*
     *  The inner static class responsible for holding the TypeRegistry Singleton instance
     */
    private static class RegistryHelper {
    	
        //  The final instance of the ListyTypes Singleton class
        private static final TypeRegistry INSTANCE = new TypeRegistry();
    }

    /**
     * Returns the single instance of the TypeRegistry
     * @return ListType Single instance of TypeRegistry
     */
    public static TypeRegistry getInstance() {
        return RegistryHelper.INSTANCE;
    }

    /**
     * Register a new enumeration type
     * @param type  Type to be registered
     * @return true if the type was registered; false if type to register is null 
     *              or there was a type with the same name already registered
     */
    public  boolean addType( EnumType type) {
    	if (type == null) {
    		throw new NullPointerException("Enum Type to add can't be null");
    	}
    	
    	String typeName = type.getName();   	
    	if (theTypes.containsKey(typeName)) {
    		return false;
    	}
    	
    	theTypes.put(typeName, type);
    	return true;
    }
    
    
    /**
     * Returns a registerd type
     * @param name Name of the type to get
     * @return EnumType the requested type
     */
    public EnumType getType( String name) {
    	return theTypes.get(name);
    }
    


	// ========================================================================================================
	@Override
	public boolean equals(Object o){
		if (this == o)
			return true;

		if (!(o instanceof Var ))
			return false;

		TypeRegistry that = (TypeRegistry) o;
		return this.theTypes.equals(that.theTypes);
	}

	@Override
	public int hashCode() { return this.theTypes.hashCode(); }

	@Override
	public String toString() { 
		StringBuilder s = new StringBuilder();
		s.append("TypeRegistry{"); 
		Iterator<String> keyIterator = theTypes.keySet().iterator();
		while (keyIterator.hasNext()) {
			String typeName = keyIterator.next();
			s.append(typeName).append(" ");
		}
		s.append("}");
		return s.toString();	
	}

}