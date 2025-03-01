package com.f.metadata.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

/**
 * Represents a schema persistent record
 */
@Entity
@Table(name = "schema", indexes = {
	    @Index(name = "idx_schema_name", columnList = "name")
	})
public class SchemaDTO {
	
	/*
	 * id        Schema id
	 * name		 Schema name
	 * fieldsXML Fields that define the schema
	 */

	@Id
	private Long        id;
	
	@Column(name = "name", nullable = false, length =40)
	private String      name;
	
	@Column(name = "fieldsXML", nullable = false, length =1000)
	private String      fieldsXML;
	
	public SchemaDTO() { }
	
	public SchemaDTO ( Long id, String name, String fieldsXML) {
		if( id == null || fieldsXML == null || fieldsXML.trim().length() == 0) {
			throw new IllegalArgumentException("SchemaDTO invalid id["+ id+ "] name["+name+ "] fieldsXML["+ fieldsXML+ "]");
		}
		this.id  = id;
		this.name= name;
		this.fieldsXML = fieldsXML;
	}
	
	public Long getId()        { return id;}
	public void setId(Long id) { this.id = id;}
	
	public String getName()            { return name;}
	public void   setName(String name) { this.name = name;}
	
	public String getFieldsXML()                 { return fieldsXML;}
	public void   setFieldsXML(String fieldsXML) { this.fieldsXML = fieldsXML;}

}
