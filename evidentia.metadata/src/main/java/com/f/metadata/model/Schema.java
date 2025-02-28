package com.f.metadata.model;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import com.f.basic.model.Identity;
import com.f.basic.model.UTIL;

 
/**
 * Represents a schema composed of a set of fields
 */
public class Schema  implements SchemaImporter, Comparable<Schema>{

	/*
	 * id       Schema id
	 * name		Schema name
	 * fields	Fields that define the schema
	 */
	private Long        id;
	private String      name;
	private Set<Field>  fields;


	/**
	 * Builds a new Schema
	 * @param name          Schema name
	 * @param securityLevel Security level
	 * @param fields        Fields that compose the schema
	 */
	public Schema(String name, Set<Field> fields) {

		if ( !UTIL.isValidName(name))
			throw new IllegalArgumentException("Illegal variable name["+ name+ "]");

		this.id     = Identity.assignId();
		this.name   = name.trim().toUpperCase();
		this.fields = (fields == null)? new TreeSet<Field>(): fields;
	}


	/**
	 * Import the schema from an external media
	 * @param importerDirector Role of Director of the import Builder
	 */
	public Schema( Schema.ImporterDirector importerDirector)
	{
		fields = new TreeSet<Field>();
		importerDirector.dirija( this);
	}
	
	
	/*
	 *  Disable null constructor 
	 */
	@SuppressWarnings("unused")
	private Schema() {
		this.name   = "";
		this.id     = -1L;
		this.fields = null;
	}
	
	
	/**
	 * Returns the schema name
	 * @return String schema name
	 */
	public String  getName() {
		return this.name;
	}
	
	
	/**
	 * Sets a name to the schema
	 * @param name New name of the schema
	 */
	@Override
	public void  setName( String name) {
		this.name = name;
	}


	/**
	 * Set up the id
	 * @param id  Identification of the schema
	 */
	@Override
	public void setId( Long id) {
		this.id = id;
	}
	
	
	
	/**
	 * Adds a new field to the schema
	 * @param field	Field to be added
	 * @return true if the field was added to the schema; false if it already existed or field is null
	 */
	@Override
	public boolean  addField( Field field) {
		if (field != null && !fields.contains(field)) {
			fields.add(field);
			return true;
		}
		return false;
	}
	
	
	
	/**
	 * Returns the number of fields in the schema
	 * @return int the schema size
	 */
	public int size() {
		return fields.size();
	}
	
	
	/**
	 * Returns an iterator to the schema fields
	 * @return Iterator<Field> Iterator to the schema fields
	 */
	public Iterator<Field> fieldIterator(){
		return fields.iterator();
	}



	// =================================================================================
	/*
	 * Builders to export/import the Schema 
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
		 * Export the basic info of the schema
		 * @param id      Identification of the schema    
		 * @param name    Schema name
		 */
		public void exportBasic( String name, Long id);

		/**
		 * Export the fields of the schema
		 * @param fieldsIterator Iterator<Role> to the required Roles
		 */
		public void exportFields( Iterator<Field> fieldsIterator);

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
	public Object export( Schema.Exporter  exporter)
	{
		exporter.initExport();
		exporter.exportBasic( name, id);
		exporter.exportFields(fields.iterator());
		exporter.endExport();
		return exporter.getProduct();

	}// export


	/**
	 * Defines the role of "Director" of the import builder of the object
	 */
	public interface ImporterDirector
	{
		public void dirija( SchemaImporter importer);
	}

	// ========================================================================================================
	@Override
	public boolean equals(Object o){
		if (this == o)
			return true;

		if (!(o instanceof Schema ))
			return false;

		Schema that = (Schema) o;
		return this.id.equals( that.id);
	}

	@Override
	public int hashCode() { 
		return this.name.hashCode()*655347;
	}

	@Override
	public String toString() { 
		StringBuilder s = new StringBuilder();
		s.append("Schema{"+ this.name).append(" Fields[");
		for (Field f: fields) {
			s.append(f.getName()).append(" ");
		}
		s.append("]}");
		return s.toString();
	}
		

	@Override
	public int compareTo(Schema other) { 
		return this.name.compareTo(other.name);
	}
	
}
