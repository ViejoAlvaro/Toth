package security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import objects.Identity;
import objects.Parm;
import security.xml.TothObjectToXML;

public class TothObjectTest {
	

	private static DataObject      theDataObject;
	private static ProcedureObject theProcedureObject;
	
	
	static {
		Identity.setId(0L);
		theDataObject      = new DataObject     (Parm.ADMIN_SECURITY_LEVEL);
		Identity.setId(0L);
		theProcedureObject = new ProcedureObject(Parm.ADMIN_SECURITY_LEVEL);

	}


	@BeforeAll
	public static void beforeAll() {
		System.out.println("))) Testing TothObject abstract");
		Identity.setId(0L);
	}


	@Test
	public void TothObjectTest1() {	
		Identity.setId(2000L);
		TothObject obj1 = new DataObject(Parm.ADMIN_SECURITY_LEVEL);
		assertTrue ( obj1.getId() == 2001);
		assertTrue ( obj1.getSecurityLevel() == Parm.ADMIN_SECURITY_LEVEL);
	}
	
	
	public void TothObjectTest2() {
		TothObject obj2 = new DataObject( 32767, Parm.PUBLIC_SECURITY_LEVEL);
		assertTrue ( obj2.getId() == 32767);
		assertTrue ( obj2.getSecurityLevel() == Parm.PUBLIC_SECURITY_LEVEL);
	}


	@Test
	public void   getIdTest() {
		Identity.setId(1L);
		DataObject aDataObject = new DataObject(Parm.ADMIN_SECURITY_LEVEL);
		assertEquals(1L, theDataObject.getId());
		assertEquals(2L, aDataObject.getId());
		Identity.setId(1L);
		ProcedureObject aProcedureObject = new ProcedureObject(Parm.ADMIN_SECURITY_LEVEL);
		assertEquals(1L, theProcedureObject.getId());
		assertEquals(2L, aProcedureObject.getId());

	}

	@Test
	public void getSecurityLevelTest() {
		Identity.setId(3L);
		DataObject aDataObject3 = new DataObject(3);
		assertEquals(Parm.ADMIN_SECURITY_LEVEL, theDataObject.getSecurityLevel());
		assertEquals(3, aDataObject3.getSecurityLevel());
		Identity.setId(3L);
		ProcedureObject aProcedureObject3 = new ProcedureObject(3);
		assertEquals(Parm.ADMIN_SECURITY_LEVEL, theProcedureObject.getSecurityLevel());
		assertEquals(3, aProcedureObject3.getSecurityLevel());

	}

	@Test
	public void setSecurityLevelTest () {
		Identity.setId(3L);
		DataObject aDataObject4 = new DataObject(4);
		assertEquals(4, aDataObject4.getSecurityLevel());
		aDataObject4.setSecurityLevel(2);
		assertEquals(2, aDataObject4.getSecurityLevel());
		Identity.setId(3L);
		ProcedureObject aProcedureObject4 = new ProcedureObject(4);
		assertEquals(4, aProcedureObject4.getSecurityLevel());
		aProcedureObject4.setSecurityLevel(2);
		assertEquals(2, aProcedureObject4.getSecurityLevel());
	}


	@Test
	public void checkSecurityLevelTest( ) {
		Identity.setId(10L);
		DataObject aDataObject5 = new DataObject(3);
		assertEquals(3, aDataObject5.getSecurityLevel());
		assertTrue(aDataObject5.checkSecurityLevel(3));
		assertTrue(aDataObject5.checkSecurityLevel(4));
		assertFalse(aDataObject5.checkSecurityLevel(2));
		Identity.setId(10L);
		ProcedureObject aProcedureObject5 = new ProcedureObject(3);
		assertEquals(3, aProcedureObject5.getSecurityLevel());
		assertTrue(aProcedureObject5.checkSecurityLevel(3));
		assertTrue(aProcedureObject5.checkSecurityLevel(4));
		assertFalse(aProcedureObject5.checkSecurityLevel(2));
	}

	@Test
	public void acceptsTest ( ) {
       // Tested in UserTest
	}
	
	
	@Test
	public void exportTest() {
		DataObject bData = new DataObject(3);
		TothObject.Exporter tothObjectToXML = new TothObjectToXML(0);  
		String actual  = (String)bData.export(tothObjectToXML);
		actual = actual.replace("\n", " ");
		String expected = "<Toth id=\"22\" level=\"3\"/> ";
		assertEquals(expected, actual); 
	}
	
	
	@Test
	public void isPublicTest() {
		Identity.setId(20L);
		DataObject aDataObject9 = new DataObject(Parm.PUBLIC_SECURITY_LEVEL);
		assertTrue( aDataObject9.isPublic());
		aDataObject9.setSecurityLevel(Parm.MAX_SECURITY_LEVEL);
		assertFalse( aDataObject9.isPublic());
	}

	
	@Test
	public void equalsTest(){
		Identity.setId(30L);
		assertEquals(theDataObject, theDataObject);
		DataObject aDataObject5 = new DataObject(Parm.ADMIN_SECURITY_LEVEL);
		assertNotEquals(theDataObject, aDataObject5);
		DataObject aDataObject6 = new DataObject(3);
		assertNotEquals(aDataObject5, aDataObject6);
		assertNotEquals(aDataObject5, null);
		assertNotEquals(aDataObject5, "OBJECT5");
		Identity.setId(30L);
		assertEquals(theProcedureObject, theProcedureObject);
		ProcedureObject aProcedureObject5 = new ProcedureObject(Parm.ADMIN_SECURITY_LEVEL);
		assertNotEquals(theProcedureObject, aProcedureObject5);
		ProcedureObject aProcedureObject6 = new ProcedureObject(3);
		assertNotEquals(aProcedureObject5, aProcedureObject6);
		assertNotEquals(aProcedureObject5, null);
		assertNotEquals(aProcedureObject5, "OBJECT6");

	}

	@Test
	public void hashCodeTest() { 
		Identity.setId(40L);
		DataObject aDataObject8 = new DataObject(3);
		DataObject aDataObject9 = new DataObject(4);
		assertFalse(aDataObject8.hashCode() == 0);
		assertFalse(aDataObject8.hashCode() == aDataObject9.hashCode());
		Identity.setId(40L);
		ProcedureObject aProcedureObject8 = new ProcedureObject(3);
		ProcedureObject aProcedureObject9 = new ProcedureObject(4);
		assertFalse(aProcedureObject8.hashCode() == 0);
		assertFalse(aProcedureObject8.hashCode() == aProcedureObject9.hashCode());

	}

	@Test
	public void toStringTest() { 
		Identity.setId(50L);
		DataObject aDataObject7 = new DataObject(4);
		assertEquals("guard{ id["+ aDataObject7.getId()+ "] level["+ aDataObject7.getSecurityLevel()+ "]}", aDataObject7.toString());
		Identity.setId(50L);
		ProcedureObject aProcedureObject7 = new ProcedureObject(4);
		assertEquals("guard{ id["+ aProcedureObject7.getId()+ "] level["+ aProcedureObject7.getSecurityLevel()+ "]}", aProcedureObject7.toString());
	}
	
	@Test
	public void compareToTest() { 
		Identity.setId(40L);
		DataObject aDataObject10 = new DataObject(3);
		DataObject aDataObject11 = new DataObject(4);
		assertTrue (aDataObject10.compareTo(aDataObject10) == 0);
		assertFalse(aDataObject10.compareTo(aDataObject11) == 0);
		assertTrue (aDataObject10.compareTo(aDataObject11)  < 0);
		Identity.setId(40L);
		ProcedureObject aProcedureObject10 = new ProcedureObject(3);
		ProcedureObject aProcedureObject11 = new ProcedureObject(4);
		assertTrue (aProcedureObject10.compareTo(aProcedureObject10) == 0);
		assertFalse(aProcedureObject10.compareTo(aProcedureObject11) == 0);
		assertTrue (aProcedureObject10.compareTo(aProcedureObject11)  < 0);
		
	}

}
