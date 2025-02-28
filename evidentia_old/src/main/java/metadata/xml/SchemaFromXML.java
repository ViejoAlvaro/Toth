package metadata.xml;

import java.io.StringReader;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

import metadata.EnumField;
import metadata.Field;
import metadata.Schema;
import metadata.SchemaImporter;
import objects.Parm;
import security.DataObject;
import util.LogHandler;
import util.UTIL;
import util.XMLContentHandler;
import util.XMLContentHandler.TagsProcessor;

public class SchemaFromXML implements Schema.ImporterDirector, TagsProcessor {

	/*
	 * importer    "Builder" role of the Schema import builder
	 * xmlSource   Schema data source in xml format
	 * schemaName  Schema name
	 * log         Logger of the system
	 * logHdler    Stream Handler of system's logger
	 */
	private SchemaImporter    importer;
	private String            xmlSource;
	private String            schemaName;
	private Set<Field>        fields;
	private static transient  Logger         log  = LogHandler.getLoggerInstance();
	private static transient  StreamHandler hdler = LogHandler.getHandlerInstance();

	public SchemaFromXML( String xmlSource ) {

		if (xmlSource  == null) {
			throw new NullPointerException("Schema's XML representation can not be null");
		}
		this.xmlSource  = xmlSource; 
		this.schemaName = "";     
		this.fields     = new TreeSet<Field>();
	}

	@Override
	public void dirija(SchemaImporter importer) {

		if (importer == null) {
			throw new NullPointerException("Schema's import 'Builder' role of Schemas can not be null");
		}
		this.importer = importer;

		try {
			SAXParserFactory  factory = SAXParserFactory.newInstance();
			SAXParser          parser = factory.newSAXParser();
			DefaultHandler    handler = new XMLContentHandler(this);
			StringReader    xmlReader = new StringReader(xmlSource);
			InputSource   inputSource = new InputSource(xmlReader);         
			parser.parse(inputSource, handler);
		} catch (Exception e) {
			log.log(Level.WARNING, "*** Error loading Schema from xml["+ xmlSource+ "]", e);
			hdler.flush();
			e.printStackTrace();
		}
	}

	// =====================================================================================
	//  start, end Tags processors
	/*
           <Schema name="THE_SCHEMA"/>
              <Toth id="1" level="3"/>
              <fields>
                 <field name="field1 type="STRING"/>
                 <field name="field2 type="INTEGER"/>
                 <field name="field3 type="BOOLEAN"/>
                 <field name="field4 type="DATE"/>
                 <field name="field5 type="LIST" enum="ENUM_NAME1"/>
              </fields>
           </Schema>       
	 */

	/**
	 * Processing start of Schema
	 * @param atts Attributes of Schema
	 */
	public void start_Schema ( Attributes atts) {
		// <Schema name="THE_SCHEMA"/>
		schemaName         = null;
		for (int i= 0; i < atts.getLength(); i++) {
			if ("name".equals(atts.getLocalName(i).toLowerCase())){
				schemaName = atts.getValue(i);
			} 
		}
		importer.setName( this.schemaName);
	}


	/**
	 * Processing end of Schema
	 * @param atts Attributes of Schema 
	 * @param contents Contents of Schema 
	 */
	public void end_Schema ( Attributes atts, String contents){
		log.finest("Loaded Schema "+ schemaName);
	}


	/**
	 * Process Schema's guard
	 * @param atts Attributes of guard
	 */
	public void start_Toth ( Attributes atts)
	{   
		// <Toth id="1" level="3"/>
		long      id = Parm.NULL_ID;
		int    level = Parm.MAX_SECURITY_LEVEL;
		for (int i= 0; i < atts.getLength(); i++)
		{
			if ("id".equals(atts.getLocalName(i).toLowerCase())) {
				id = Long.valueOf(atts.getValue(i));
			}else if ("level".equals(atts.getLocalName(i).toLowerCase())) {
				level = Integer.valueOf(atts.getValue(i));
			} 
		}
		DataObject guard = new DataObject(id, level);
		importer.setGuard(guard);
	}


	/**
	 * End of guard
	 * @param atts Attributes of guard 
	 * @param contents Contents of guard 
	 */
	public void end_Toth( Attributes atts, String contents){
	}


	/**
	 * Start fields
	 * @param atts Attributes of requiredRoles 
	 */
	public void start_fields ( Attributes atts) {
		// <fields>   	  
		this.fields.clear();
	}


	/**
	 * End of fields
	 * @param atts Attributes of fields 
	 * @param contents Contents of fields 
	 */
	public void end_fields( Attributes atts, String contents) {
		for( Field field: this.fields) {
			importer.addField(field);
		}
	}



	/**
	 * Start field
	 * @param atts Attributes of field 
	 */
	public void start_field ( Attributes atts) {
		// <field name="field4 type="DATE"/>
		// <field name="field5 type="LIST" enum="ENUM_NAME1"/>
		String  name     = "";
		String  type     = "";
		String  enumName = "";
		Field   field    = null;
		for (int i= 0; i < atts.getLength(); i++)
		{
			if ("name".equals(atts.getLocalName(i).toLowerCase())) {
				name = atts.getValue(i);
			}else if ("type".equals(atts.getLocalName(i).toLowerCase())) {
				type = atts.getValue(i);
			}else if ("enum".equals(atts.getLocalName(i).toLowerCase())) {
				enumName = atts.getValue(i);
			}  
		}
		if(UTIL.isValidName(enumName)) {
			field = new EnumField(name, enumName);
		}else {
			field = new Field(name, Parm.TYPE.valueOf(type));
		}
		fields.add(field);

	}


	/**
	 * End of field
	 * @param atts Attributes of field 
	 * @param contents Contents of field 
	 */
	public void end_field( Attributes atts, String contents) {
	}

}
