package com.f.security.model.classification;

import java.util.ArrayList;
import java.util.List;

import com.f.security.model.User;
import com.f.security.model.object.DataObject;


public class Leaf extends DataObject implements Component, Hierarchy{

	/*
	 * parent	  Parent group node
	 */
	private  Branch  parent;
	

	/**
	 * Builds a new simple node of the composite
	 * @param securityLevel  security level associated to the leaf
	 */
	public Leaf(int securityLevel) {	
		super(securityLevel);
		this.parent	=  null;
	}
	
	
	/**
	 * Disable null constructor
	 */
	@SuppressWarnings("unused")
	private Leaf() {}


	/**
	 * Returns the security guard of the leaf
	 * @return security guard 
	 */
	public DataObject getGuard() {
		return this;
	}


	@Override
	/** 
	 * Returns the identification of the leaf
	 * @return  identification of the leaf
	 */
	public long getId() {
		return super.getId();
	}
	

	/**
	 * Returns the security level of the leaf
	 * @return  security level of the leaf
	 */
	@Override
	public int getSecurityLevel() {
		return super.getSecurityLevel();
	}
	

	/**
	 * Updates the security level of the leaf
	 */
	@Override
	public void setSecurityLevel(int securityLevel) {
		super.setSecurityLevel(securityLevel);		
	}
	
	
	/**
	 * Returns the parent of the branch
	 * @return Branch parent
	 */
	@Override
	public Branch getParent()  { 
		return this.parent;
	}


	/**
	 * Adds the node to a parent node
	 * @param parentNode  node to which current node will be added as a child node
	 * @return Branch  parent node
	 */
	public Branch addToParent( Branch parentNode) {
		if (parentNode == null)
			throw new IllegalArgumentException("Parent node can't be null");

		this.parent = parentNode;
		parentNode.addChild(this);
		return parent;
	}


	/**
	 * Gets the hierarchical path to the node.
	 * The path is ordered from root (first) to the node (last)
	 * @return List with the parent path
	 */
	protected List<Component> parentPath(){
		ArrayList<Component> path = new ArrayList<Component>();
		Branch node = this.parent;
		while( node != null) { 
			path.addFirst(node);
			node = node.getParent();
		}
		path.addFirst(this);
		return path;
	}


	/**
	 * Gets the root of hierarchy the node belongs to
	 * @return  Group root node of the hierarchy 
	 */
	public Branch   getRoot(){
		Branch node = this.getParent();
		while ( node.parent != null) {
			node = node.parent;
		}
		return node;
	}


	// ========================================================================================================
	// Security logic

	/**
	 * Checks if presented security level allows access to the object
	 * @param securityLevel  level of user who wants access to the object
	 * @return  true if the user level allows access to the object; false otherwise
	 */
	@Override
	public boolean checkSecurityLevel( int userLevel) {
		
		List<Component> path =  parentPath();		
		for (Component node: path) {
			if ( userLevel < node.getSecurityLevel()) {
				return false;
			}			
		}
		return true;
	}


	/**
	 * Decides if the node accepts access by a user
	 * @param subject   User that wants access to this object
	 * @return  boolean if access is granted; false otherwise
	 */
	@Override
	public boolean accepts (User subject) { 
		if (subject == null)
			throw new NullPointerException("User that wants access cannot be null");

		if ( !checkSecurityLevel(subject.getSecurityLevel())) {
			return false;
		}

		List<Component> path = parentPath();
		for ( Component node: path) {
			if ( node instanceof ProcedureObject) {
				if (subject.performRightsAllowAccess((ProcedureObject)node)){
					return true;
				}
			}else if (subject.dataRightsAllowAccess((DataObject)node)){
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

		if (!(o instanceof Leaf ))
			return false;

		Leaf that = (Leaf) o;
		return this.getId() == that.getId();

	}

	@Override
	public int hashCode() { 
		return super.hashCode(); 
	}

	@Override
	public String toString() { 
		return "Leaf{id["+ getId()+ "] level["+ getSecurityLevel()+ "] parent["+ (parent == null? "root": parent.getId())+ "]}";
	}

}
