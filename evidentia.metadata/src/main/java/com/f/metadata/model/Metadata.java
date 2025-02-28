package com.f.metadata.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.f.basic.model.UTIL;


/**
 * Represents a Metadata composed of set of variables with values
 */
public class Metadata implements Comparable<Metadata>{

	/**
	 * schema	Schema descriptors of the variables
	 * values	Values of the schema fields
	 */
	private String       name;
	private final Schema schema;
	private List<String> values;

	
	/**
	 * Builds an instance of 
	 * @param name      Metadata name
	 * @param schema	Descriptors of the variables
	 * @param values    Values of the variables
	 */
	public Metadata( String name, Schema schema, List<String> values) {

		if ( ! UTIL.isValidName(name)) {
			throw new IllegalArgumentException("Invalid metadata schema name["+ name+ "]");
		}
			
		if (schema == null) {
			throw new NullPointerException("Schema can not be null");
		}

		if (values == null || values.size() != schema.size() ) {
			throw new IllegalArgumentException("The number of values must match the number of fields in the schema["+ schema.size()+ "]");
		}
		
		this.name   =  name.trim().toUpperCase();
		this.schema =  schema;
		this.values =  new ArrayList<String>();
		this.values.addAll(values);
	}
	
	
	/**
	 * Returns the Metadata name
	 * @return String Metadata name
	 */
	public String getName() {
		return this.name;
	}
	
	

	/**'
	 * Returns the schema of the Metadata
	 * @return Schema Metadata schema
	 */
	public Schema	getSchema() {
		return this.schema;
	}
	
	
	
	/**
	 * Returns an iterator to the variables of the metadata
	 * @return  Iterator<Var>  Variable iterator
	 */
	public  Iterator<Var> varIterator(){
		List<Var> vars = new ArrayList<Var>();
		Iterator<Field> fieldIter = schema.fieldIterator();
		Iterator<String> valIter  = values.iterator();
		while (fieldIter.hasNext()) {
			vars.add( new Var(fieldIter.next(), valIter.next()));
		}
	    return vars.iterator();	
	}
	

	// =================================================================================
	/*
	 * Builders to export the Object 
	 *  
	 * A builder has two components "Builder" and "Director". 
	 * An export "Builder" knows the external structure of the export media, 
	 * and the "Director" knows the internal structure of the object. 
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
		 * Exports the metadata fields
		 * @param name Metadata name
		 * @param schema Metadata schema
		 */
		public void exportBasic(String name, Schema schema);


		/**
		 * Export the values of the schema
		 * @param valueIterator  Iterator to the metadata values
		 */
		public void exportValues( Iterator<String> valueIterator);
		
		
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
	public Object export( Metadata.Exporter  exporter)
	{
		exporter.initExport();
		exporter.exportBasic( name, schema);
		exporter.exportValues( values.iterator());
		exporter.endExport();
		return exporter.getProduct();
	}// export


	// ========================================================================================================
	@Override
	public boolean equals(Object o){
		if (this == o)
			return true;

		if (!(o instanceof Metadata ))
			return false;

		Metadata that = (Metadata) o;
		if ( !this.name.equals(that.name) || this.values.size() != that.values.size()) {
			return false;
		}
		java.util.Iterator<String> otherValuesIter = that.values.iterator();
		for (String tv: this.values) {
			String ov = otherValuesIter.next();
			if( !tv.equals(ov)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() { return this.name.hashCode()*655347; }

	@Override
	public String toString() { 
		return "Metadata{ name["+ this.name+ "] schema["+ schema.getName()+ "] values#="+ values.size()+ "}";
	}

	@Override
	public int compareTo(Metadata other) { 
		return this.name.compareTo(other.name);
	}


}
