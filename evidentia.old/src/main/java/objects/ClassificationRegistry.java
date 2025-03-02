package objects;
import java.util.Iterator;
import java.util.TreeMap;

/**
 * Represents the Registry of all classification schemas
 */
public class ClassificationRegistry {
	
	/*
	 * theClassifications<ClassificationName, Classification>  Registered Classifications
	 */
	private TreeMap<String, Classification>  theClassifications;

    /**
     * Null constructor can only be called internally
     */
    private ClassificationRegistry() { 
    	theClassifications = new TreeMap<String, Classification>();
    }

    /*
     *  The inner static class responsible for holding the ClassificationRegistry Singleton instance
     */
    private static class RegistryHelper {
    	
        //  The final instance of the Classifications Singleton class
        private static final ClassificationRegistry INSTANCE = new ClassificationRegistry();
    }

    /**
     * Returns the single instance of the ClassificationRegistry
     * @return ClassificationRegistry Single instance of ClassificationRegistry
     */
    public static ClassificationRegistry getInstance() {
        return RegistryHelper.INSTANCE;
    }
    
    
    /**
     * Clean up the register
     * (Use only for testing purposes)
     */
    public void clear() {
    	theClassifications.clear();
    }

    /**
     * Register a new Classification
     * @param Classification  Type to be registered
     * @return true if the Classification was registered; false if Classification to register is null 
     *              or there was a Classification with the same name already registered
     */
    public  boolean addClassification( Classification classification) {
    	if (classification == null) {
    		throw new NullPointerException("Classification to register can't be null");
    	}
    	
    	String classificationName = classification.getName();   	
    	if (theClassifications.containsKey(classificationName)) {
    		return false;
    	}
    	
    	theClassifications.put(classificationName, classification);
    	return true;
    }
    
    
    /**
     * Returns a registered Classification
     * @param ClassificationName Name of the Classification to get
     * @return Classification the requested Classification
     */
    public Classification getClassification( String classificationName) {
    	return theClassifications.get(classificationName);
    }
    


	// ========================================================================================================
	@Override
	public boolean equals(Object o){
		if (this == o)
			return true;

		if (!(o instanceof ClassificationRegistry ))
			return false;

		ClassificationRegistry that = (ClassificationRegistry) o;
		return this.theClassifications.equals(that.theClassifications);
	}

	@Override
	public int hashCode() { return this.theClassifications.hashCode(); }

	@Override
	public String toString() { 
		StringBuilder s = new StringBuilder();
		s.append("ClassificationRegistry{"); 
		Iterator<String> keyIterator = theClassifications.keySet().iterator();
		while (keyIterator.hasNext()) {
			String classificationName = keyIterator.next();
			s.append(classificationName).append(" ");
		}
		s.append("}");
		return s.toString();	
	}



}
