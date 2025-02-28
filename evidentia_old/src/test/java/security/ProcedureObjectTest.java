package security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import objects.Identity;
import objects.Parm;

public class ProcedureObjectTest {

	private static ProcedureObject theObject;
	static {
		Identity.setId(0L);
		theObject = new ProcedureObject(Parm.ADMIN_SECURITY_LEVEL);
	}


	@BeforeAll
	public static void beforeAll() {
		System.out.println("))) Testing ProcedureObject");
		Identity.setId(0L);
	}



	@Test
	public void   getIdTest() {
		Identity.setId(1L);
		ProcedureObject anObject = new ProcedureObject(Parm.ADMIN_SECURITY_LEVEL);
		assertEquals(1L, theObject.getId());
		assertEquals(2L, anObject.getId());

	}

	@Test
	public void getSecurityLevelTest() {
		Identity.setId(30L);
		ProcedureObject anObject3 = new ProcedureObject(3);
		assertEquals(Parm.ADMIN_SECURITY_LEVEL, theObject.getSecurityLevel());
		assertEquals(3, anObject3.getSecurityLevel());

	}

	@Test
	public void setSecurityLevelTest () {
		Identity.setId(40L);
		ProcedureObject anObject4 = new ProcedureObject(4);
		assertEquals(4, anObject4.getSecurityLevel());
		anObject4.setSecurityLevel(2);
		assertEquals(2, anObject4.getSecurityLevel());
	}



	@Test
	public void checkSecurityLevelTest( ) {
		Identity.setId(50L);
		ProcedureObject anObject5 = new ProcedureObject(3);
		assertEquals(3, anObject5.getSecurityLevel());
		assertTrue(anObject5.checkSecurityLevel(3));
		assertTrue(anObject5.checkSecurityLevel(4));
		assertFalse(anObject5.checkSecurityLevel(2));
	}

	@Test
	public void acceptsTest ( ) {
         // Tested in UserTest
	}

	
	@Test
	public void equalsTest(){
		Identity.setId(60L);
		assertEquals(theObject, theObject);
		ProcedureObject anObject5 = new ProcedureObject(Parm.ADMIN_SECURITY_LEVEL);
		assertNotEquals(theObject, anObject5);
		ProcedureObject anObject6 = new ProcedureObject(3);
		assertNotEquals(anObject5, anObject6);
		assertNotEquals(anObject5, null);
		assertNotEquals(anObject5, "OBJECT5");

	}

	@Test
	public void hashCodeTest() { 
		Identity.setId(70L);
		ProcedureObject anObject8 = new ProcedureObject(3);
		ProcedureObject anObject9 = new ProcedureObject(4);
		assertFalse(anObject8.hashCode() == 0);
		assertFalse(anObject8.hashCode() == anObject9.hashCode());

	}

	@Test
	public void toStringTest() { 
		Identity.setId(10L);
		ProcedureObject anObject7 = new ProcedureObject(4);
		assertEquals("guard{ id["+ anObject7.getId()+ "] level["+ anObject7.getSecurityLevel()+ "]}", anObject7.toString());
	}
	
	@Test
	public void compareToTest() { 
		Identity.setId(40L);
		ProcedureObject anObject10 = new ProcedureObject(3);
		ProcedureObject anObject11 = new ProcedureObject(4);
		assertTrue (anObject10.compareTo(anObject10) == 0);
		assertFalse(anObject10.compareTo(anObject11) == 0);
		assertTrue (anObject10.compareTo(anObject11)  < 0);
	}

}
