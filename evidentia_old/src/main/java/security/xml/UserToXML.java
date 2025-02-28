package security.xml;

import java.util.Iterator;

import metadata.Metadata;
import metadata.xml.MetadataToXML;
import objects.Parm;
import security.Role;
import security.TothObject;
import security.User.Exporter;
import util.UTIL;

public class UserToXML implements Exporter {

   private StringBuilder       xml = new StringBuilder(1200);
   private TothObject.Exporter tothToXML;
   private Metadata.Exporter   metadataToXML;
   private int iLevel = 0;
   private String is;

   @Override
   public void initExport() {
      xml.setLength(0);
      iLevel = 0;
      xml.append("<User");
      is = UTIL.indentString(++iLevel);
   }

   @Override
   public void exportBasic(TothObject guard, String userId, String firstName, String lastName, String email) {
      xml.append(" code=\"").append(userId).append("\"");
      xml.append(" lastName=\"").append(lastName).append("\"");
      xml.append(" firstName=\"").append(firstName).append("\"");
      xml.append(" email=\"").append(email).append("\">\n");
      this.tothToXML = new TothObjectToXML(iLevel);
      xml.append(guard.export(tothToXML));
   }

   @Override
   public void exportRequiredRoles(Iterator<Role> requiredRoles) {
      xml.append(is).append("<requiredRoles>\n");
      is = UTIL.indentString(++iLevel);
      while(requiredRoles.hasNext()) {
         Role r = requiredRoles.next();
         xml.append(is).append("<requiredRole r=\"").append(r.getName()).append("\" />\n");
      }
      is = UTIL.indentString(--iLevel);
      xml.append(is).append("</requiredRoles>\n");
   }

   @Override
   public void exportEnablingRoles(Iterator<Role> enablingRoles) {
      xml.append(is).append("<enablingRoles>\n");
      is = UTIL.indentString(++iLevel);
      while(enablingRoles.hasNext()) {
         Role r = enablingRoles.next();
         xml.append(is).append("<enablingRole e=\"").append(r.getName()).append("\" />\n");
      }
      is = UTIL.indentString(--iLevel);
      xml.append(is).append("</enablingRoles>\n");
   }

   @Override
   public void exportMetadata(Metadata metadata) {
	  metadataToXML = new MetadataToXML(iLevel);
      xml.append(metadata.export(metadataToXML));
   }

   @Override
   public void endExport() {
      is = " ".repeat((--iLevel) * Parm.INDENT);
      xml.append(is).append("</User>\n");
   }

   @Override
   public Object getProduct() {
      return xml.toString();
   }

}
