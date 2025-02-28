package objects;

import security.DataObject;
import security.User;
import util.UTIL;

/**
 * Represents a data classification scheme
 */
public class Classification implements Comparable<Classification>{

	/*
	 * name   name of the classification schema
	 * guard  security guard
	 * root   root of the classification scheme
	 */
	private String      name;
	private DataObject  guard;
	private Branch      root;

	/**
	 * Builds a new classification scheme
	 * @param securityLevel  security level of the scheme
	 */
	public Classification (int securityLevel, String name) {
		if ( !UTIL.isValidName(name)) {
			throw new IllegalArgumentException("Illegal Classification name["+ name+ "]");
		}
		this.name  = name.trim().toUpperCase();
		this.guard = new DataObject(securityLevel);
		this.root  = new Branch(securityLevel);
	}

	/**
	 * Disable null constructor
	 */
	@SuppressWarnings("unused")
	private Classification() { }


	/**
	 * Returns the name of the classification schema
	 * @return String name of the classification schema
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns the root node of the classification schema
	 * @return root node of classification schema
	 */
	public Branch getRoot() {
		return this.root;
	}



	/**
	 * Adds a new branch to the rights composite
	 * @param branch The branch to be added
	 * @param parent Parent of the branch branch
	 */
	public Branch addBranch( Branch branch, Branch parent) {
		if (branch == null) {
			throw new NullPointerException("Branch to add to the classification can not be null");
		}
		if (! findComponent(parent)) {
			throw new IllegalArgumentException("Parent of Branch not found in classification["+ parent.toString()+ "]");
		}
		branch.addToParent(parent);
		return branch;

	}


	/**
	 * Adds a new leaf to the classification schema
	 * @param securityLevel Security level of the leaf
	 * @param leaf   Leaf to add
	 * @param parent Parent of the leaf
	 */
	public Leaf addLeaf( Leaf leaf, Branch parent) {
		if (leaf == null) {
			throw new NullPointerException("Leaf to add to the classification can not be null");
		}
		if (! findComponent(parent)) {
			throw new IllegalArgumentException("Parent of Leaf not found in classification["+ parent.toString()+ "]");
		}
		leaf.addToParent(parent);
		return leaf;

	}

	/**
	 * Finds a component node of the classification schema
	 * @param branch  The component we want to locate
	 * @return true if the classification contains the component; false otherwise
	 */
	public boolean findComponent(Component component) {
		return root.contains(component);

	}

	// ========================================================================================================
	// Security	

	/**
	 * Decides if a user can access the classification 
	 * @param subject  User that wants to access the classification
	 * @return  true if the user is granted access; false otherwise
	 */
	public boolean  accepts( User subject) {
		return guard.accepts(subject);
	}

	// ========================================================================================================
	@Override
	public boolean equals(Object o){
		if (this == o)
			return true;

		if (!(o instanceof Classification ))
			return false;

		Classification that = (Classification) o;
		return this.guard == that.guard && this.name.equals(that.name);

	}

	@Override
	public int hashCode() { 
		return guard.hashCode(); 
	}

	@Override
	public String toString() { 
		return "Classification{ name["+ name+ "] "+ guard.toString()+" root["+ root.toString()+ "]}";
	}

	@Override
	public int compareTo(Classification that){ 
		return this.equals(that)?  0 : this.name.compareTo(that.name); 
	}


}
