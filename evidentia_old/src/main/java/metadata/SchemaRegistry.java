package metadata;

import java.util.Iterator;
import java.util.TreeMap;


/**
 * Represents the unique Registry of Schemas
 */
public class SchemaRegistry {
	
	/*
	 * theSchemas<schemaName, schema>  Registered schemas
	 */
	private TreeMap<String, Schema>  theSchemas;

    /**
     * Null constructor can only be called internally
     */
    private SchemaRegistry() { 
    	theSchemas = new TreeMap<String, Schema>();
    }

    /*
     *  The inner static class responsible for holding the SchemaRegistry Singleton instance
     */
    private static class RegistryHelper {
    	
        //  The final instance of the Schemas Singleton class
        private static final SchemaRegistry INSTANCE = new SchemaRegistry();
    }

    /**
     * Returns the single instance of the SchemaRegistry
     * @return SchemaRegistry Single instance of SchemaRegistry
     */
    public static SchemaRegistry getInstance() {
        return RegistryHelper.INSTANCE;
    }
    
    
    /**
     * Clean up the register
     * (Use only for testing purposes)
     */
    public void clear() {
    	theSchemas.clear();
    }

    /**
     * Register a new schema
     * @param schema  Schema to be registered
     * @return true if the schema was registered; false if schema to register is null 
     *              or there was a schema with the same name already registered
     */
    public  boolean addSchema( Schema schema) {
    	if (schema == null) {
    		throw new NullPointerException("Schema to register can't be null");
    	}
    	
    	String schemaName = schema.getName();   	
    	if (theSchemas.containsKey(schemaName)) {
    		return false;
    	}
    	
    	theSchemas.put(schemaName, schema);
    	return true;
    }
    
    
    /**
     * Returns a registered schema
     * @param name Name of the schema to get
     * @return Schema the requested schema
     */
    public Schema getSchema( String schemaName) {
    	return theSchemas.get(schemaName);
    }
    


	// ========================================================================================================
	@Override
	public boolean equals(Object o){
		if (this == o)
			return true;

		if (!(o instanceof SchemaRegistry ))
			return false;

		SchemaRegistry that = (SchemaRegistry) o;
		return this.theSchemas.equals(that.theSchemas);
	}

	@Override
	public int hashCode() { return this.theSchemas.hashCode(); }

	@Override
	public String toString() { 
		StringBuilder s = new StringBuilder();
		s.append("SchemaRegistry{"); 
		Iterator<String> keyIterator = theSchemas.keySet().iterator();
		while (keyIterator.hasNext()) {
			String schemaName = keyIterator.next();
			s.append(schemaName).append(" ");
		}
		s.append("}");
		return s.toString();	
	}

}
