package metadata.xml;

import java.util.Iterator;

import metadata.EnumField;
import metadata.Field;
import metadata.Schema.Exporter;
import objects.Parm;
import security.DataObject;
import security.TothObject;
import security.xml.TothObjectToXML;
import util.UTIL;

public class SchemaToXML implements Exporter {

	   private StringBuilder       xml = new StringBuilder(1200);
	   private TothObject.Exporter tothToXML;
	   private int                 iLevel;
	   private String              is;

	   
	   
	@Override
	public void initExport() {
	      xml.setLength(0);
	      xml.append("<Schema");
	      is = UTIL.indentString(++iLevel);
	}

	@Override
	public void exportBasic(String name, DataObject guard) {
	      xml.append(" name=\"").append(name).append("\">\n");
	      this.tothToXML = new TothObjectToXML(iLevel);
	      xml.append(guard.export(tothToXML));
	}

	@Override
	public void exportFields(Iterator<Field> fieldsIterator) {
	      xml.append(is).append("<fields>\n");
	      is = UTIL.indentString(++iLevel);
	      while(fieldsIterator.hasNext()) {
	         Field f = fieldsIterator.next();
	         xml.append(is).append("<field name=\"").append(f.getName()).append( "\" type=\"").append(f.getType());
	         if (f instanceof EnumField) {
	        	 xml.append("\" enum=\"").append(((EnumField)f).enumTypeName());
	         }
        	 xml.append("\"/>\n");
	      }
	      is = UTIL.indentString(--iLevel);
	      xml.append(is).append("</fields>\n");
	}

	@Override
	public void endExport() {
	      is = " ".repeat((--iLevel) * Parm.INDENT);
	      xml.append(is).append("</Schema>\n");
	}

	@Override
	public Object getProduct() {
	      return xml.toString();
	}

}
