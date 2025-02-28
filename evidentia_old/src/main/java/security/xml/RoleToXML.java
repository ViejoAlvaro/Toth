package security.xml;

import security.DataObject;
import security.Rights;
import security.Role.Exporter;
import security.TothObject;
import util.UTIL;

public class RoleToXML implements Exporter {

	   private StringBuilder       xml = new StringBuilder(1200);
	   private TothObject.Exporter tothToXML;
	   private int iLevel = 0;
	   private String  is;

	@Override
	public void initExport() {
	      xml.setLength(0);
	      xml.append("<Role");
	      iLevel = 0;
	      is = UTIL.indentString(++iLevel);
	}

	@Override
	public void exportBasic(String name, DataObject guard) {
	      xml.append(" name=\"").append(name).append("\">\n");
	      this.tothToXML = new TothObjectToXML(iLevel);
	      xml.append(guard.export(tothToXML));
	}

	@Override
	public void exportRights(Rights dataAccessRights, Rights performRights) {
		Rights.Exporter dataRightsExporter    =  new RightsToXML(iLevel, "DataRights");
		xml.append(is).append(dataAccessRights.export(dataRightsExporter));
		Rights.Exporter performRightsExporter =  new RightsToXML(iLevel, "ProcRights");
		xml.append(is).append(performRights.export(performRightsExporter));
	    is = UTIL.indentString(++iLevel);

	}

	@Override
	public void endExport() {		
		xml.append("</Role>\n");

	}

	@Override
	public Object getProduct() {
		return xml.toString();
	}

}
