package security.xml;

import java.util.Iterator;

import objects.Classification;
import objects.Parm;
import security.DataObject;
import security.Rights;
import util.UTIL;

public class RightsToXML implements Rights.Exporter {

	private static final  int   LINE_SIZE = 180;
	private StringBuilder       xml;
	private int                 iLevel = 0;
	private String              is;
	private StringBuilder       line;
	private String              rightsName;

	/**
	 * Builds a new exporter to XML
	 * @param iLevel Current indentation level
	 */
	public RightsToXML( int iLevel,  String rightsName) {
		this.xml        = new StringBuilder(10000);
		this.line       = new StringBuilder(LINE_SIZE);
		this.rightsName = rightsName;
		this.iLevel     = iLevel;
	}
	


	@Override
	public void initExport() {
		xml.setLength(0);
		xml.append("<").append(rightsName).append( " ");
		is = UTIL.indentString(++iLevel);
	}
	

	@Override
	public void exportBasic( Classification classification) {
		xml.append(" classification=\"").append(classification.getName()).append("\">\n");
		resetLine();
	}
	

	@Override
	public void exportRights(Iterator<DataObject> rightsIterator) {
		while(rightsIterator.hasNext()) {
			resetLine();
			DataObject right = rightsIterator.next();
			line.append(String.valueOf(right.getId()))
			   .append("/")
			   .append(right.getSecurityLevel())
			   .append(" ");
		}
	}
	

	@Override
	public void endExport() {
		if (line.length() > Parm.INDENT) {
			xml.append(line).append("\n");
		}
		is = " ".repeat((--iLevel) * Parm.INDENT);
		xml.append(is).append("</").append(this.rightsName).append(">\n");
	}
	

	@Override
	public Object getProduct() {
		return xml.toString();
	}


	/*
	 * cleans and initializes the line buffer
	 */
	private void resetLine() {
		if( this.line.length() >= LINE_SIZE) {
			this.xml.append(line).append("\n");
			this.line.append(is);
		}
	}

}
