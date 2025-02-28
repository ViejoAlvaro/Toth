package metadata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import objects.Parm;

public class EnumFieldTest {
	
	
	static private EnumType theType2;
	static private EnumField theField2;
	static {
 		Set<String> range2 = new TreeSet<String>();
		range2.add("X");
		range2.add("Y");
		range2.add("Z");
		range2.add("s");
		range2.add("t");
		range2.add("w");
		range2.add("4");
		range2.add("5");
		range2.add("6"); 
		theType2  = new EnumType ( "ENUMTYPE2", range2);
		EnumRegistry.getInstance().addType(theType2);
        theField2 = new EnumField("FIELD2", "ENUMTYPE2");
	}
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("))) Testing EnumField");
	}
	
	

	@Test 
	public void EnumFieldTest1() {
		EnumType theType1;
		Set<String> range1 = new TreeSet<String>();
		range1.add("A");
		range1.add("B");
		range1.add("C");
		range1.add("d");
		range1.add("e");
		range1.add("f");
		range1.add("1");
		range1.add("2");
		range1.add("3");
		theType1 = new EnumType ( "ENUMTYPE1", range1);
		EnumRegistry.getInstance().addType(theType1);		
		EnumField theField1 = new EnumField("FIELD1", "ENUMTYPE1");

        assertTrue (theField1.equals(theField1));
        assertFalse(theField1.equals(theField2));
        
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
	        EnumField f3 = new EnumField("FIELD3", "XYZ");
			assertTrue(f3.getType().equals(Parm.TYPE.STRING));
		    });
		String expectedMessage = "Enum type name[XYZ] is not registered";
		String actualMessage = exception.getMessage();
	    assertTrue(actualMessage.contains(expectedMessage));
        
	}
	

	@Test
	public void enumTypeNameTest() {
		EnumField theField1 = new EnumField("FIELD1", "ENUMTYPE1");
		assertTrue(theField1.enumTypeName().equals("ENUMTYPE1"));
		assertTrue(theField2.enumTypeName().equals("ENUMTYPE2"));
		assertFalse(theField1.enumTypeName().equals("ENUMTYPE2"));
		assertFalse(theField2.enumTypeName().equals("ENUMTYPE1"));
		assertFalse(theField1.enumTypeName().equals("XYZ"));
	}
	

	@Test
	public void getEnumTypeTest() {
		EnumField theField1 = new EnumField("FIELD1", "ENUMTYPE1");
		EnumType type1 = theField1.getEnumType();
		EnumType theType1 = EnumRegistry.getInstance().getType("ENUMTYPE1");
		assertEquals   (type1, theType1);
		assertNotEquals(type1, theType2);
		EnumType type2 = theField2.getEnumType();
		assertTrue( theType2.equals(type2));
	}
	

	@Test
	public void equalsTest(){
		EnumField theField1 = new EnumField("FIELD1", "ENUMTYPE1");
		assertTrue (theField1.equals(theField1));
		assertFalse(theField1.equals(theField2));

	}
	

	@Test
	public void hashCodeTest() {
		EnumField theField1 = new EnumField("FIELD1", "ENUMTYPE1");
		assertFalse( theField1.hashCode() == 0);
		assertTrue ( theField1.hashCode() != theField2.hashCode());
	}
	

	@Test 
	public void toStringTest() {
		EnumField theField1 = new EnumField("FIELD1", "ENUMTYPE1");
		assertEquals(theField1.toString(), "EnumField{ name[field1] type[LIST] enumTypeName[ENUMTYPE1]}");
		assertEquals(theField2.toString(), "EnumField{ name[field2] type[LIST] enumTypeName[ENUMTYPE2]}");

	}

}
