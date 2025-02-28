package metadata;

import security.DataObject;

public interface SchemaImporter {


	/**
	 * Sets a name to the schema
	 * @param name New name of the schema
	 */
	public void  setName( String name);
	

	/**
	 * Set up the security guard
	 * @param guard guard of the schema object
	 */
	public void setGuard( DataObject  guard);


	/**
	 * Adds a new field to the schema
	 * @param field	Field to be added
	 * @return Field the field added to the schema; null if the field to add is null
	 */
	public Field  addField( Field field);

}
