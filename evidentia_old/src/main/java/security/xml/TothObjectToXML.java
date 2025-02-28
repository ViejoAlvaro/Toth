package security.xml;

import security.TothObject;
import util.UTIL;

public class TothObjectToXML implements TothObject.Exporter {

	/*
	 * xml            result of the export procedure
	 * is             current indentation string
	 */
	private StringBuilder xml;
	private String        is; 

	/**
	 * Builds an XML exporter of TothObjects
	 * @param iLevel initial indentation level
	 */
	public TothObjectToXML( int iLevel ) {
		this.xml    = new StringBuilder(60);
		this.is     = UTIL.indentString(iLevel);
	}


	@Override
	public void initExport() {
		xml.setLength(0);
		xml.append(is).append("<Toth");
	}


	@Override
	public void exportObject(long objectId, int securityLevel) {
		xml.append(" id=\"").append(objectId).append("\"");
		xml.append(" level=\"").append(securityLevel).append("\"");
	}


	@Override
	public void endExport() {
		xml.append("/>\n");
	}


	@Override
	public Object getProduct() {
		return xml.toString();
	}

}
