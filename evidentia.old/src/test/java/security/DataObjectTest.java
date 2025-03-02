package security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import objects.Identity;
import objects.Parm;

public class DataObjectTest {

	private static DataObject theObject;
	static {
		Identity.setId(0L);
		theObject = new DataObject(Parm.ADMIN_SECURITY_LEVEL);
	}


	@BeforeAll
	public static void beforeAll() {
		System.out.println("))) Testing DataObject");
		Identity.setId(0L);
	}



	@Test
	public void   getIdTest() {
		Identity.setId(1L);
		DataObject anObject = new DataObject(Parm.ADMIN_SECURITY_LEVEL);
		assertEquals(1L, theObject.getId());
		assertEquals(2L, anObject.getId());

	}
	
	@Test
	public void getGuardTest() {
		Identity.setId(1L);
		DataObject anObject = new DataObject(Parm.ADMIN_SECURITY_LEVEL);
		assertEquals(anObject, anObject.getGuard());
		assertFalse(theObject.equals(anObject.getGuard()));
	}

	@Test
	public void getSecurityLevelTest() {
		Identity.setId(3L);
		DataObject anObject3 = new DataObject(3);
		assertEquals(Parm.ADMIN_SECURITY_LEVEL, theObject.getSecurityLevel());
		assertEquals(3, anObject3.getSecurityLevel());

	}

	@Test
	public void setSecurityLevelTest () {
		Identity.setId(3L);
		DataObject anObject4 = new DataObject(4);
		assertEquals(4, anObject4.getSecurityLevel());
		anObject4.setSecurityLevel(2);
		assertEquals(2, anObject4.getSecurityLevel());
	}



	@Test
	public void checkSecurityLevelTest( ) {
		DataObject anObject5 = new DataObject(3);
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
		assertEquals(theObject, theObject);
		DataObject anObject5 = new DataObject(Parm.ADMIN_SECURITY_LEVEL);
		assertNotEquals(theObject, anObject5);
		DataObject anObject6 = new DataObject(3);
		assertNotEquals(anObject5, anObject6);
		assertNotEquals(anObject5, null);
		assertNotEquals(anObject5, "OBJECT5");

	}

	@Test
	public void hashCodeTest() { 
		Identity.setId(20L);
		DataObject anObject8 = new DataObject(3);
		DataObject anObject9 = new DataObject(4);
		assertFalse(anObject8.hashCode() == 0);
		assertFalse(anObject8.hashCode() == anObject9.hashCode());

	}

	@Test
	public void toStringTest() { 
		Identity.setId(10L);
		DataObject anObject7 = new DataObject(4);
		assertEquals("guard{ id["+ anObject7.getId()+ "] level["+ anObject7.getSecurityLevel()+ "]}", anObject7.toString());
	}
	
	@Test
	public void compareToTest() { 
		Identity.setId(40L);
		DataObject anObject10 = new DataObject(3);
		DataObject anObject11 = new DataObject(4);
		assertFalse(anObject10.compareTo(anObject11) == 0);
		
	}



}
