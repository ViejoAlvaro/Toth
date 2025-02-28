package com.f.metadata.model.xml;

import java.util.Iterator;

import com.f.basic.model.Parm;
import com.f.basic.model.UTIL;
import com.f.metadata.model.EnumField;
import com.f.metadata.model.Field;
import com.f.metadata.model.Schema;


public class SchemaToXML implements Schema.Exporter {

	   private StringBuilder       xml = new StringBuilder(1200);
	   private int                 iLevel;
	   private String              is;

	   
	   
	@Override
	public void initExport() {
	      xml.setLength(0);
	      xml.append("<Schema");
	      is = UTIL.indentString(++iLevel);
	}

	@Override
	public void exportBasic(String name, Long id) {
	      xml.append(" name=\"").append(name).append("\"")
	         .append(" id=\""+id).append("\">\n");
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
