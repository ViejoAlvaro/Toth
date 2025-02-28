package com.f.security.model.classification;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.f.security.model.User;
import com.f.security.model.object.DataObject;


public class Branch extends DataObject implements Component, Hierarchy {

	/*
	 * parent			Parent group node
	 * children         Set of components of the composite
	 */
	protected Branch          parent;
	protected Set<Component>  children;


	/**
	 * Builds a new Branch object
	 * @param securityLevel  security level associated to the object
	 */
	public Branch(int securityLevel) {
		super(securityLevel);
		this.children      =   new HashSet<Component>();
		this.parent		   =   null;
	}


	/**
	 * Disable null constructor
	 */
	@SuppressWarnings("unused")
	private Branch() { }


	/**
	 * Returns the security guard of the leaf
	 * @return security guard 
	 */
	@Override
	public DataObject getGuard() {
		return this;
	}


	/**
	 * Returns the id of the node
	 * @return Object's id
	 */
	@Override
	public long   getId() {
		return super.getId();
	}


	/**
	 * Returns the security  level associated to the branch
	 * @return Node's security level
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
	 * Returns the parent of the branch
	 * @return Branch parent
	 */

	public Set<Component> getComponents()  { 
		return this.children;
	}


	/**
	 * Adds the branch to a parent branch
	 * @param parentNode  Branch to which current brach will be added as a child 
	 * @return Branch  parent branch
	 */
	public Branch addToParent( Branch parentNode) {
		if (parentNode == null)
			throw new IllegalArgumentException("Parent node can't be null");

		if ( parentNode.equals(this))
			throw new IllegalArgumentException("Parent node is equal to current node["+ getId()+ "]");

		if (isChild(parentNode))
			throw new IllegalArgumentException("Parent node ["+ parentNode.getId()+ "] is a child of the node we want to add["+ getId()+ "]");

		this.parent = parentNode;
		parentNode.addChild(this);
		return parent;
	}


	/**
	 * Adds a component to the children of current branch
	 * @param  component   Component child to add
	 */
	public void  addChild( Component component) {
		children.add(component);
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
			node = node.parent;
		}
		path.addFirst(this);
		return path;
	}


	/**
	 * Gets the root of hierarchy the node belongs to
	 * @return  Group root node of the hierarchy 
	 */
	public Branch   getRoot(){
		Branch node = this;
		while ( node.parent != null) {
			node = node.parent;
		}
		return node;
	}



	/*
	 * Checks if a node is a child of current node
	 * @param Branch node to be checked
	 * @return  true if checked node is child of current node
	 */
	private boolean isChild( Branch node) {
		if (node == null)
			return false;

		for (Branch n = node; n != null; n = n.parent) {
			if (this.equals(n))
				return true;			
		}
		return false;
	}


	/**
	 * Decides if the branch contains a component
	 * @param component The component to lookup
	 * @return  true if the branch contains the component; false otherwise
	 */
	public boolean contains( Component component) {
		if ( this.equals(component) || children.contains(component) )
			return true;

		for ( Component c: children) {
			if ( c instanceof Branch) {
				Branch branch = (Branch)c;
				if ( branch.contains(component))
					return true;
			}
		}
		return false;

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
	public boolean accepts (User subject){ 
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

		if (!(o instanceof Branch ))
			return false;

		Branch that = (Branch) o;
		return this.getId() == that.getId();
	}

	@Override
	public int hashCode() { return super.hashCode(); }

	@Override
	public String toString() { return "Branch{"+ super.toString()+ " parent["+ (parent == null? "nodeIsroot": parent.getId())+ "] children #="+ children.size()+ "}";}

}
