package metadata.xml;

import java.util.Iterator;

import metadata.Metadata;
import metadata.Schema;
import util.UTIL;

public class MetadataToXML implements Metadata.Exporter {

	/*
	 * xml            result of the export procedure
	 * iLevel         current indentation level
	 * is             current indentation string
	 */
	private StringBuilder xml;
	private int           iLevel;
	private String        is; 

	public MetadataToXML( int iLevel) {
		this.xml    = new StringBuilder(512);
		this.iLevel = iLevel;
	}

	@Override
	public void initExport() {
		xml.setLength(0);
		is = UTIL.indentString(iLevel);
		xml.append(is).append("<metadata");
		is = UTIL.indentString(++iLevel);
	}

	@Override
	public void exportBasic(String name, Schema schema) {
		xml.append(" name=\"").append(name).append("\"");
		xml.append(" schema=\"").append(schema.getName()).append("\">\n");
	}

	@Override
	public void exportValues(Iterator<String> valueIterator) {
		xml.append(is).append("<values>\n");
		is = UTIL.indentString(++iLevel);
		while(valueIterator.hasNext()) {
			String v = valueIterator.next();
			if (UTIL.isValidStringValue(v)) {
				xml.append(is).append("<value v=\"").append(v).append("\"/>\n");
			} else {
				xml.append(is).append("<value><![CDATA[").append(v).append("]]></value>\n");
			}
		}
		is = UTIL.indentString(--iLevel);
		xml.append(is).append("</values>\n");
	}

	@Override
	public void endExport() {
		is = UTIL.indentString(--iLevel);
		xml.append(is).append("</metadata>\n");
	}

	@Override
	public Object getProduct() {
		return xml.toString();	}

}
