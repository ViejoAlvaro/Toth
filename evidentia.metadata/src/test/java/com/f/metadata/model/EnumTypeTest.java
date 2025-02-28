package com.f.metadata.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
	public void EnumTypeTest2() {
		EnumType type3 = new EnumType("type3");
		type3.addEntry("abc", "Entry abc", 10);
		type3.addEntry("cde", "Entry cde", 11);
		type3.addEntry("efg", "Entry efg", 12);
		type3.addEntry("ghi", "Entry ghi", 13);
		type3.addEntry("ijk", "Entry ijk", 14);
		
		assertEquals( "Entry abc",         type3.getDescription("abc"));
		assertEquals( Integer.valueOf(13), type3.getOrdinal("ghi"));
		assertEquals( "Missing Data",      type3.getDescription("MD"));
		assertEquals( "Not applicable",    type3.getDescription("NA"));
		assertEquals( "Inconsistent",      type3.getDescription("IN"));
				
	}

	
	@Test
	public void getNameTest() {
		EnumType type2 = new EnumType ( "type2", range);
		String type2Name = type2.getName();
		assertTrue ( type2Name.equals("TYPE2"));
		assertFalse( type2Name.equals("type2"));
		assertFalse( type2Name.equals(null));
		assertFalse( type2Name.equals("otherName"));

	}
	
	
	@Test
	public void getDescriptionTest() {
		EnumType type4 = new EnumType("type4");
		type4.addEntry("abc", "Entry abc", 10);
		type4.addEntry("cde", "Entry cde", 11);
		type4.addEntry("efg", "Entry efg", 12);
		type4.addEntry("ghi", "Entry ghi", 13);
		type4.addEntry("ijk", "Entry ijk", 14);
		
		assertEquals   ("Entry ghi", type4.getDescription("ghi"));
		assertNotEquals("ghi",       type4.getDescription("ghi"));
		assertNotNull  ( type4.getDescription("ghi"));	
	}
	
	
	@Test
	public void getOrdinalTest() {
		EnumType type5 = new EnumType("type5");
		type5.addEntry("abc", "Entry abc", 10);
		type5.addEntry("cde", "Entry cde", 11);
		type5.addEntry("efg", "Entry efg", 12);
		type5.addEntry("ghi", "Entry ghi", 13);
		type5.addEntry("ijk", "Entry ijk", 14);
		
		assertEquals   (Integer.valueOf(14), type5.getOrdinal("ijk"));
		assertNotEquals(Integer.valueOf(0),  type5.getOrdinal("ijk"));
		assertNotNull  ( type5.getOrdinal("ijk"));	
		
	}
	
	
	@Test
	public void addEntryTest() {
		EnumType type6 = new EnumType("type6");
		type6.addEntry("abc", "Entry abc", 10);
		type6.addEntry("cde", "Entry cde", 11);
		type6.addEntry("efg", "Entry efg", 12);
		type6.addEntry("ghi", "Entry ghi", 13);
		type6.addEntry("ijk", "Entry ijk", 14);
		
		assertEquals   (Integer.valueOf(12), type6.getOrdinal("efg"));
		assertEquals   ("Entry cde", type6.getDescription("cde"));
		assertNotNull  (type6.getOrdinal("ijk"));
		assertNotEquals("Entry ijk", type6.getDescription("cde"));
		
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
		assertTrue( type10.toString().equals("Type{ name[TYPE10] values[\n"
				+ "1 1 1\n"
				+ "2 2 2\n"
				+ "3 3 3\n"
				+ "A 4 A\n"
				+ "B 5 B\n"
				+ "C 6 C\n"
				+ "D 7 D\n"
				+ "E 8 E\n"
				+ "IN -997 Inconsistent\n"
				+ "MD -999 Missing Data\n"
				+ "NA -998 Not applicable\n"
				+ "]}"));
		
	}

	@Test
	public void compareToTest() {
		EnumType type7 = new EnumType("type7");
		type7.addEntry("v1", "Entry v1", 1);
		type7.addEntry("v2", "Entry v2", 2);
		EnumType type8 = new EnumType("type8");
		type8.addEntry("v10", "Entry v10", 100);
		type8.addEntry("v20", "Entry v20", 200);
		
		int compare = type7.compareTo(type8);
		assertTrue(compare < 0);
		assertTrue(type7.compareTo(type7)== 0);
		assertTrue(type8.compareTo(type7) > 0);

	}
	
	
	@Test
	public void specialValuesTest() {
		EnumType type20 = new EnumType("type20");
		type20.addEntry("v31", "Entry v31", 31);
		type20.addEntry("v32", "Entry v32", 32);
		
		String key1 = type20.getKey("MD");
		String key2 = type20.getKey("NA");
		String key3 = type20.getKey("IN");
		
		assertEquals("MD", key1);
		assertEquals("NA", key2);
		assertEquals("IN", key3);
		
		String desc1 = type20.getDescription("MD");
		String desc2 = type20.getDescription("NA");
		String desc3 = type20.getDescription("IN");
		
		assertEquals("Missing Data",  desc1);
		assertEquals("Not applicable",desc2);
		assertEquals("Inconsistent",  desc3);
		
		Integer ord1 = type20.getOrdinal("MD");
		Integer ord2 = type20.getOrdinal("NA");
		Integer ord3 = type20.getOrdinal("IN");
		
		assertEquals( Integer.valueOf(-999), ord1);
		assertEquals( Integer.valueOf(-998), ord2);
		assertEquals( Integer.valueOf(-997), ord3);
		
	}
	
	
	@Test
	public void entryTest1() {
		EnumType type7 = new EnumType("type7");
		type7.addEntry("v1", "Entry v1", 100);
		type7.addEntry("v2", "Entry v2", 110);
		
		String desc1 = type7.getDescription("v1");
		String desc2 = type7.getDescription("v2");
		String descMD= type7.getDescription("MD");
		Integer ord1 = type7.getOrdinal("v1");
		Integer ord2 = type7.getOrdinal("v2");
		Integer ordMD= type7.getOrdinal("MD");
		
		assertNotNull(desc1);
		assertNotNull(desc2);
		assertNotEquals(desc1, desc2);
		assertNotEquals(ord1,  ord2);
		assertEquals(desc1, "Entry v1");
		assertEquals(desc2, "Entry v2");
		assertEquals(descMD, "Missing Data");
		assertEquals(ord1,  Integer.valueOf(100));
		assertEquals(ord2,  Integer.valueOf(110));
		assertEquals(ordMD, Integer.valueOf(-999));
		
	}
	
	
	@Test
	public void getEntryKeyTest() {
		EnumType type8 = new EnumType("type8");
		type8.addEntry("v10", "Entry v10", 1);
		type8.addEntry("v20", "Entry v20", 2);
		
		String key1 = type8.getKey("v10");
		String key2 = type8.getKey("v20");
		String key3 = type8.getKey("v30");
		String keyNA= type8.getKey("NA");
		
		assertNotNull(key1);
		assertNotNull(key2);
		assertNull   (key3);
		assertNotEquals(key1, key2);
		assertEquals("v10", key1);
		assertEquals("v20", key2);
		assertEquals("NA",  keyNA);
	}
	
	
	@Test
	public void getEntryDescriptionTest() {
		EnumType type9 = new EnumType("type9");
		type9.addEntry("v100", "Entry v100", 10);
		type9.addEntry("v200", "Entry v200", 20);
		
		String desc1 = type9.getDescription("v100");
		String desc2 = type9.getDescription("v200");
		String desc3 = type9.getDescription("v300");
		String descIN= type9.getDescription("IN");
		
		assertNotNull(desc1);
		assertNotNull(desc2);
		assertNull   (desc3);
		assertNotNull(descIN);
		assertNotEquals(desc1, desc2);
		assertEquals("Entry v100", desc1);
		assertEquals("Entry v200", desc2);
		assertEquals("Inconsistent", type9.getDescription("IN"));
		
		
	}

	
	@Test
	public void getEntryOrdinalTest() {
		EnumType type10 = new EnumType("type10");
		type10.addEntry("v110", "Entry v110", 110);
		type10.addEntry("v210", "Entry v210", 210);
		
		Integer ord1 = type10.getOrdinal("v110");
		Integer ord2 = type10.getOrdinal("v210");
		Integer ord3 = type10.getOrdinal("v300");
		
		assertNotNull(ord1);
		assertNotNull(ord2);
		assertNull   (ord3);
		assertNotEquals(ord1, ord2);
		assertEquals(Integer.valueOf(110), ord1);
		assertEquals(Integer.valueOf(210), ord2);
		
	}

}
