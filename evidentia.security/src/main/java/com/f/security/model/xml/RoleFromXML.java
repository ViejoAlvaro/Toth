package com.f.security.model.xml;

import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

import com.f.basic.model.LogHandler;
import com.f.basic.model.Parm;
import com.f.basic.model.XMLContentHandler;
import com.f.basic.model.XMLContentHandler.TagsProcessor;
import com.f.security.model.Rights;
import com.f.security.model.Role;
import com.f.security.model.RoleImporter;
import com.f.security.model.classification.Classification;
import com.f.security.model.classification.ClassificationRegistry;
import com.f.security.model.classification.ProcedureObject;
import com.f.security.model.object.DataObject;


public class RoleFromXML implements Role.ImporterDirector, TagsProcessor {

	/*
	 * importer    "Builder" role of the Role import builder
	 * xmlSource   Role data source in xml format
	 * roleName    User code (user id)
	 * log         Logger of the system
	 * logHdler    Stream Handler of system's logger
	 */
	private DataObject        guard;
	private RoleImporter      importer;
	private String            xmlSource;
	private String            roleName;
	private Rights            dataRights;
	private Rights            performRights;
	private ClassificationRegistry classificationRegistry;
	private static transient  Logger         log  = LogHandler.getLoggerInstance();
	private static transient  StreamHandler hdler = LogHandler.getHandlerInstance();


	public RoleFromXML(String xmlSource) {

		if (xmlSource  == null) {
			throw new NullPointerException("User's XML representation can not be null");
		}
		this.xmlSource     = xmlSource; 
		this.roleName      = "";     
		this.dataRights    = null;
		this.performRights = null;
		this.classificationRegistry = ClassificationRegistry.getInstance();

	}



	@Override
	public void dirija(RoleImporter importer) {

		if (importer == null) {
			throw new NullPointerException("Role's import 'Builder'-role of roles can not be null");
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
			log.log(Level.WARNING, "*** Error loading Role from xml["+ xmlSource+ "]", e);
			hdler.flush();
			e.printStackTrace();
		}
	}

	// =====================================================================================
	//  start, end Tags processors

	/**
	 * Processing start of Role
	 * @param atts Attributes of Role
	 */
	public void start_Role ( Attributes atts) {
		// <User code="USER3001" lastName="Boccelli" firstName="Andrea"/>
		this.guard  = null;
		roleName    = null;
		for (int i= 0; i < atts.getLength(); i++) {
			if ("name".equals(atts.getLocalName(i).toLowerCase())){
				roleName = atts.getValue(i);
			}
		}
		importer.setName( this.roleName);
	}


	/**
	 * Processing end of Role
	 * @param atts Attributes of Role 
	 * @param contents Contents of Role 
	 */
	public void end_Role ( Attributes atts, String contents){

		log.finest("Loaded Role "+ roleName);
	}


	/**
	 * Process user's guard
	 * @param atts Attributes of guard
	 */
	public void start_Toth ( Attributes atts)
	{   
		// <Toth id="3001" level="3"/>
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
     	this.guard = new DataObject(id, level);
     	importer.setGuard(this.guard);
	}


	/**
	 * End of guard
	 * @param atts Attributes of guard 
	 * @param contents Contents of guard 
	 */
	public void end_Toth( Attributes atts, String contents){
	}


	/**
	 * Process user's data rights
	 * @param atts Attributes of data rights
	 */
	public void start_DataRights ( Attributes atts)
	{   
		// <Toth id="3001" level="3"/>
		String classificationName = "";
		for (int i= 0; i < atts.getLength(); i++)
		{
			if ("classification".equals(atts.getLocalName(i).toLowerCase())) {
				classificationName = atts.getValue(i);
			}
		}
		Classification dataClassification = classificationRegistry.getClassification(classificationName);
		dataRights = new Rights(guard.getSecurityLevel(), dataClassification);
	}


	/**
	 * End of data rights
	 * @param atts Attributes of data rights 
	 * @param contents Contents of data rights 
	 */
	public void end_DataRights( Attributes atts, String contents){
		String rights[]= contents.split("[ \n]");
		for( int i = 0; i < rights.length; i++) {
			int slash = rights[i].indexOf('/');
			long id            = Long.valueOf(rights[i].substring(0,slash));
			int  securityLevel = Integer.valueOf(rights[i].substring(slash+1));
			DataObject dataRight = new DataObject(id,securityLevel);
			this.dataRights.grant(dataRight);
		}
		importer.setDataRights(dataRights);
	}


	/**
	 * Process user's perform rights
	 * @param atts Attributes of perform rights
	 */
	public void start_ProcRights ( Attributes atts)
	{   
		// <Toth id="3001" level="3"/>
		String classificationName = "";
		for (int i= 0; i < atts.getLength(); i++)
		{
			if ("classification".equals(atts.getLocalName(i).toLowerCase())) {
				classificationName = atts.getValue(i);
			}
		}
		Classification procClassification = classificationRegistry.getClassification(classificationName);
		performRights = new Rights(guard.getSecurityLevel(), procClassification);
	}


	/**
	 * End of data rights
	 * @param atts Attributes of data rights 
	 * @param contents Contents of data rights 
	 */
	public void end_ProcRights( Attributes atts, String contents){
		String rights[]= contents.split("[ \n]");
		for( int i = 0; i < rights.length; i++) {
			int slash = rights[i].indexOf('/');
			long id            = Long.valueOf(rights[i].substring(0,slash));
			int  securityLevel = Integer.valueOf(rights[i].substring(slash+1));
			ProcedureObject procRight = new ProcedureObject(id,securityLevel);
			this.performRights.grant(procRight);
		}
		importer.setPerformRights(performRights);
	}

}
