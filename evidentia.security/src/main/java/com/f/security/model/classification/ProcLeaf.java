package com.f.security.model.classification;

import java.util.List;

import com.f.security.model.User;

/**
 * Represents a leaf of a procedure classification schema
 */
public class ProcLeaf extends Leaf implements ProcTag{

	/**
	 * Builds a new simple node of the composite
	 * @param securityLevel  security level associated to the leaf
	 */
	public ProcLeaf(int securityLevel) {	
		super(securityLevel);
	}

	@Override
	public boolean accepts(User subject) {
		if (subject == null)
			throw new NullPointerException("User that wants access cannot be null");

		List<Component> path = parentPath();
		for (Component node: path) {
			if ( !subject.securityLevelAllowsAccess(node.getGuard()))
				return false;
		}

		for ( Component node: path) {
			if (subject.performRightsAllowAccess(node.getGuard())){
				return true;
			}
		}
		return false;
	}

}
