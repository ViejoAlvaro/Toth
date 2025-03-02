package security.xml;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
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

import metadata.Metadata;
import metadata.Schema;
import metadata.SchemaRegistry;
import objects.Parm;
import security.DataObject;
import security.Role;
import security.RoleRegistry;
import security.User;
import security.UserImporter;
import util.LogHandler;
import util.XMLContentHandler;
import util.XMLContentHandler.TagsProcessor;


/**
 * "Director" role of the import builder of a user from XML 
 */
public class UserFromXML implements User.ImporterDirector, TagsProcessor{

   /*
    * importer    "Builder" role of the User import builder
    * xmlSource   User data source in xml format
    * userCode    User code (user id)
    * log         Logger of the system
    * logHdler    Stream Handler of system's logger
    */
   private UserImporter      importer;
   private String            xmlSource;
   private String            userCode;
   private String            metadataName;
   private String            schemaName;
   private Set<String>       reqRoles;
   private Set<String>       enabRoles;
   private List<String>      values;
   private static transient  Logger         log  = LogHandler.getLoggerInstance();
   private static transient  StreamHandler hdler = LogHandler.getHandlerInstance();

   public UserFromXML( String xmlSource ) {

      if (xmlSource  == null) {
         throw new NullPointerException("User's XML representation can not be null");
      }
      this.xmlSource = xmlSource; 
      this.userCode  = "";     
      this.reqRoles  = new TreeSet<String>();
      this.enabRoles = new TreeSet<String>();
      this.values    = new ArrayList<String>();
   }

   @Override
   public void dirija(UserImporter importer) {

      if (importer == null) {
         throw new NullPointerException("User's import 'Builder' role of users can not be null");
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
         log.log(Level.WARNING, "*** Error loading User from xml["+ xmlSource+ "]", e);
         hdler.flush();
         e.printStackTrace();
      }
   }

   // =====================================================================================
   //  start, end Tags processors
   /*
       <User code="USER3001" lastName="Boccelli" firstName="Andrea" email="Andrea.Boccelli@gmail.com">
          <Toth id="3001" level="3"/>
          <requiredRoles>
             <requiredRole r="required_role1" />
          </requiredRoles>
          <enablingRoles>
             <enablingRole e="enabling_role1" />
          </enablingRoles>
          <metadata name="THE_META" schema="THE_SCHEMA">
             <values>
                <value v="TRUE"/>
                <value v="2025-02-03T06:03:01.501476700"/>
                <value v="12345"/>
                <value v="A"/>
                <value v="STRING_VALUE"/>
             </values>
          </metadata>
       </User>
    */

   /**
    * Processing start of User
    * @param atts Atributes of User
    */
   public void start_User ( Attributes atts) {
      // <User code="USER3001" lastName="Boccelli" firstName="Andrea"/>
      userCode         = null;
      String firstName = "";
      String lastName  = "";
      String email     = "";
      for (int i= 0; i < atts.getLength(); i++) {
         if ("code".equals(atts.getLocalName(i).toLowerCase())){
            userCode = atts.getValue(i);
         } else if ("firstname".equals(atts.getLocalName(i).toLowerCase())){
            firstName =atts.getValue(i);
         } else if ("lastname".equals(atts.getLocalName(i).toLowerCase())){
            lastName =atts.getValue(i);
         } else if ("email".equals(atts.getLocalName(i).toLowerCase())){
            email =atts.getValue(i);
         }
      }
      importer.setBasic( this.userCode, firstName, lastName, email);
   }


   /**
    * Processing end of User
    * @param atts Attributes of User 
    * @param contents Contents of User 
    */
   public void end_User ( Attributes atts, String contents){
      log.finest("Loaded User "+ userCode);
   }


   /**
    * Process user's guard
    * @param atts Atributes of guard
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
    * Start requiredRoles
    * @param atts Attributes of requiredRoles 
    */
   public void start_requiredRoles ( Attributes atts) {
      // <requiredRoles>
      this.reqRoles.clear();
   }


   /**
    * End of requiredRoles
    * @param atts Attributes of requiredRoles 
    * @param contents Contents of requiredRoles 
    */
   public void end_requiredRoles( Attributes atts, String contents) {
      for( String reqRole: reqRoles) {
         Role requiredRole = RoleRegistry.getInstance().getRole(reqRole);
         importer.addRequiredRole(requiredRole);
      }
   }



   /**
    * Start enablingRoles
    * @param atts Attributes of enablingRoles 
    */
   public void start_enablingRoles ( Attributes atts) {
      // <enablingRoles>
      this.enabRoles.clear();
   }


   /**
    * End of enablingRoles
    * @param atts Attributes of enablingRoles 
    * @param contents Contents of enablingRoles 
    */
   public void end_enablingRoles( Attributes atts, String contents) {
      for( String enabRole: enabRoles) {
         Role enablingRole = RoleRegistry.getInstance().getRole(enabRole);
         importer.addEnablingRole(enablingRole);
      }
   }


   /**
    * Start of requiredRole
    * @param atts Attributes of requiredRole 
    */
   public void start_requiredRole ( Attributes atts)
   {
      // <requiredRole r="required_role1"/>
      String roleName = "";
      for (int i= 0; i < atts.getLength(); i++)
      {
         if ("r".equals(atts.getLocalName(i).toLowerCase())) {
            roleName = atts.getValue(i);
         }
         reqRoles.add(roleName);
      }
   }


   /**
    * End of requiredRole
    * @param atts Attributes of requiredRole 
    * @param contents Contents of requiredRole 
    */
   public void end_requiredRole( Attributes atts, String contents)
   {
   }


   /**
    * Start of enablingRole
    * @param atts Attributes of enablingRole 
    */
   public void start_enablingRole ( Attributes atts)
   {
      // <enablingRole r="enabling_role1"/>
      String roleName = "";
      for (int i= 0; i < atts.getLength(); i++)
      {
         if ("e".equals(atts.getLocalName(i).toLowerCase())) {
            roleName = atts.getValue(i);
         }
         enabRoles.add(roleName);
      }
   }


   /**
    * End of enablingRole
    * @param atts Attributes of enablingRole 
    * @param contents Contents of enablingRole 
    */
   public void end_enablingRole( Attributes atts, String contents)
   {
   }




   /**
    * Start of metadata
    * @param atts Attributes of metadata 
    */
   public void start_metadata ( Attributes atts)
   {
      // <metadata name="THE_META" schema="THE_SCHEMA"/>
      this.metadataName   = "";
      this.schemaName     = "";
      for (int i= 0; i < atts.getLength(); i++) {
         if ("name".equals(atts.getLocalName(i).toLowerCase())){
            this.metadataName = atts.getValue(i);
         } else if ("schema".equals(atts.getLocalName(i).toLowerCase())){
            this.schemaName =atts.getValue(i);
         }
      }
   }


   /**
    * End of metadata
    * @param atts Attributes of metadata 
    * @param contents Contents of metadata 
    */
   public void end_metadata( Attributes atts, String contents)
   {
      //  Build metadata
      // importer.setMetadata(Metadata metadata);
   }




   /**
    * Start values
    * @param atts Attributes of values 
    */
   public void start_values ( Attributes atts)
   {
      // <values>
      values.clear();
   }


   /**
    * End values
    * @param atts Attributes of values 
    * @param contents Contents of values 
    */
   public void end_values( Attributes atts, String contents)
   {
      Schema schema = SchemaRegistry.getInstance().getSchema(this.schemaName);
      Metadata meta = new Metadata(this.metadataName, schema, this.values);
      importer.setMetadata(meta);
   }




   /**
    * Start value
    * @param atts Attributes of value 
    */
   public void start_value ( Attributes atts)
   {
      // <value v="TRUE"/>
      // <value v="2025-02-03T06:03:01.501476700"/>
      // <value v="12345"/>
      // <value v="A"/>
      // <value v="STRING_VALUE"/>

      for (int i= 0; i < atts.getLength(); i++)
      {
         if ("v".equals(atts.getLocalName(i).toLowerCase())){
            values.add(atts.getValue(i));
         }
      }
   }


   /**
    * End value
    * @param atts Attributes of value 
    * @param contents Contents of value 
    */
   public void end_value( Attributes atts, String contents)
   {
   }

}
