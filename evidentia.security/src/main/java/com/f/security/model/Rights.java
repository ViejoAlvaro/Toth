package com.f.security.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.f.basic.model.Parm;
import com.f.security.model.classification.Classification;
import com.f.security.model.classification.Component;
import com.f.security.model.object.DataObject;



/**
 * Represents a set of access rights
 */
public class Rights {

	/*
	 * classification Classification schema. Source of the data or procedures to be accessed
	 * theRights      set of access rights
	 */
	private Classification   classification;
	private Set<DataObject>  theRights;


	/** 
	 * Builds the access rights
	 */	
	public Rights(int securityLevel, Classification classification) {
		if ( classification == null) {
			throw new NullPointerException("Classification schema can not be null");
		}

		this.classification = classification;
		this.theRights      = new HashSet<DataObject>();
	}
	
	/** 
	 * Builds a null access rights
	 */		
	public Rights() {
		this.classification = new  Classification (Parm.MAX_SECURITY_LEVEL, "_NULL_CLASSIFICATION_");
	}

	// ========================================================================================================
	//   Security logic


	/**
	 * Grants access to a data object
	 * @param anObject object whose access right is granted
	 */
	public void grant( DataObject anObject) {
		if (anObject == null)
			return;

		if ( !this.classification.findComponent(anObject))
			throw new IllegalArgumentException( "The classification schema does not contain the object["+ anObject.getId()+ "]");

		theRights.add(anObject);			
	}


	/**
	 * Revokes access grant from an object
	 * @param anObject Data object whose access right is revoked
	 */
	public void revoke( DataObject anObject) {
		theRights.remove(anObject);
	}


	/**
	 * Checks if the access rights grants access to an object
	 * @param anObject Data object whose access right is checked
	 * @return true if the access rights grant access to the object; false otherwise
	 */
	public boolean isGrantedAccess( DataObject anObject) {
		boolean isPublic     = anObject.isPublic();
		boolean isAuthorized = theRights.contains(anObject);
		return isPublic || isAuthorized;
	}


	/**
	 * Checks if the access rights grants access to a component
	 * @param component Component whose access right is checked
	 * @return true if the access rights grant access to the component; false otherwise
	 */
	public boolean isGrantedAccess( Component component) {
		return theRights.contains(component.getGuard());
	}

	
	/**
	 * Returns the number of rights granted
	 * @return Number of rights granted
	 */
	public int getRightsNumber() {
		return theRights.size();
	}



	// =================================================================================
	/*
	 * Builders to export/import the Data RightsITF 
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
		 * Export the basic info of the right
		 * @param classification  Classification schema
		 */
		public void exportBasic( Classification classification);

		/**
		 * Export the rights 
		 * @param rightsIterator roles rights to data
		 */
		public void exportRights( Iterator<DataObject> rightsIterator);

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
	public Object export( Rights.Exporter  exporter)
	{
		exporter.initExport();
		exporter.exportBasic( classification);
		exporter.exportRights( theRights.iterator());
		exporter.endExport();
		return exporter.getProduct();

	}// export


	// ========================================================================================================
	@Override
	public boolean equals(Object o){
		if (this == o)
			return true;

		if (!(o instanceof Rights ))
			return false;

		Rights other = (Rights)o;
		return this.classification.equals(other.classification) 
			&& this.theRights.equals(other.theRights);

	}


	@Override
	public int hashCode() { return classification.hashCode()* 27327 + theRights.hashCode()*65537; }

	@Override
	public String toString() { 
		StringBuilder s = new StringBuilder(120);
		s.append("Rights{")
		 .append(" schema[").append(classification.getName()).append("]")
		 .append(" rights#="+ theRights.size())
		 .append("}");
		
		return s.toString();
	}


}
