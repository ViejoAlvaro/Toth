package objects;

import java.util.List;

import security.User;

/**
 * Represents a branch of a data classification schema
 */
public class DataBranch extends Branch {

	/**
	 * Builds a new branch node of the composite
	 * @param securityLevel  security level associated to the branch
	 */
	public DataBranch(int securityLevel) {	
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
			if (subject.dataRightsAllowAccess(node.getGuard())){
				return true;
			}
		}
		return false;
	}

}
