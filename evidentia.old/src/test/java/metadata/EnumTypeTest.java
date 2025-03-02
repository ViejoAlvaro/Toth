package metadata;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class EnumTypeTest {
	
	private static Set<String> range = new TreeSet<String>();
	static {
		range.add("A");
		range.add("B");
		range.add("C");
		range.add("D");
		range.add("E");
		range.add("1");
		range.add("2");
		range.add("3");
	}
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("))) Testing EnumType");
	}
	
	@Test
	public void EnumTypeTest1() {	
		EnumType type1 = new EnumType ( "type1", range);
		assertTrue( type1.isOfType("A"));
		assertTrue( type1.isOfType("B"));
		assertTrue( type1.isOfType("C"));
		assertTrue( type1.isOfType("D"));
		assertTrue( type1.isOfType("D"));
		assertTrue( type1.isOfType("1"));
		assertTrue( type1.isOfType("2"));
		assertTrue( type1.isOfType("3"));
		assertFalse(type1.isOfType("X"));
		assertFalse(type1.isOfType("Y"));
		assertFalse(type1.isOfType("Z"));
	}

	@Test
	public void getNameTest() {
		EnumType type2 = new EnumType ( "type2", range);
		String type2Name = type2.getName();
		assertTrue( type2Name.equals("TYPE2"));
		assertFalse( type2Name.equals("type2"));
		assertFalse( type2Name.equals(null));
		assertFalse( type2Name.equals("otherName"));

	}

	@Test
	public void isOfTypeTest() {
		EnumType type3 = new EnumType ( "type3", range);
		assertTrue( type3.isOfType("A"));
		assertTrue( type3.isOfType("B"));
		assertTrue( type3.isOfType("C"));
		assertTrue( type3.isOfType("D"));
		assertTrue( type3.isOfType("E"));
		assertFalse(type3.isOfType("X"));
		assertFalse(type3.isOfType("Y"));
		assertFalse(type3.isOfType("Z"));
		assertFalse(type3.isOfType("4"));
		assertFalse(type3.isOfType("5"));
		assertFalse(type3.isOfType("6"));

	}

	@Test
	public void equalsTest() {
		EnumType type4 = new EnumType ( "type4", range);
		EnumType type5 = new EnumType ( "type5", range);		
		assertTrue(type4.equals(type4));
		assertFalse( type4.equals(type5));
		Set<String> range2 = new TreeSet<String>();
		range2.add("A");
		range2.add("B");
		range2.add("C");
		range2.add("D");
		range2.add("E");
		range2.add("1");
		range2.add("2");
		range2.add("3");
		EnumType type6 = new EnumType( "type6", range2);
		EnumType type7 = new EnumType( "type5", range2);
		assertFalse(type4.equals(type6));
		assertTrue(type5.equals(type7));


	}

	@Test
	public void hashCodeTest() {
		EnumType type8 = new EnumType ( "type8", range);
		EnumType type9 = new EnumType ( "type9", range);
		assertFalse(type8.hashCode() == 0);
		assertTrue(type8.hashCode() != type9.hashCode());
	}
	
	
	@Test
	public void toStringTest() {
		EnumType type10 = new EnumType ("type10", range);
		assertTrue( type10.toString().equals("Type{ name[TYPE10] values[1 2 3 A B C D E ]}"));
		
	}

	@Test
	public void compareToTest() {

	}

	


}
