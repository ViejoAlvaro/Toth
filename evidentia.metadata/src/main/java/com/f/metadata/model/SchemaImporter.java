package com.f.metadata.model;


public interface SchemaImporter {


	/**
	 * Sets a name to the schema
	 * @param name New name of the schema
	 */
	public void  setName( String name);
	

	/**
	 * Set up the schema id
	 * @param id   Identification of the schema
	 */
	public void setId(Long id);


	/**
	 * Adds a new field to the schema
	 * @param field	Field to be added
	 * @return true if the field was added to the schema; false if it already existed or field is null
	 */
	public boolean  addField( Field field);

}
