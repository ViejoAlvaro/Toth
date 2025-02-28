package com.f.metadata.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.f.basic.model.Parm;


public class FieldTest {
	
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("))) Testing Field");
	}
	

	@Test 
	public void Field1Test() {
		Field field1 = new Field( "field1", Parm.TYPE.BOOLEAN);
		assertTrue("field1".equals(field1.getName()));
		assertTrue(field1.getType().equals(Parm.TYPE.BOOLEAN));
		Field field2 = new Field( "field2", Parm.TYPE.DATE);
		assertTrue("field2".equals(field2.getName()));
		assertTrue(field2.getType().equals(Parm.TYPE.DATE));
		Field field3 = new Field( "field3", Parm.TYPE.INTEGER);
		assertTrue("field3".equals(field3.getName()));
		assertTrue(field3.getType().equals(Parm.TYPE.INTEGER));
		Field field4 = new Field( "field4", Parm.TYPE.LIST);
		assertTrue("field4".equals(field4.getName()));
		assertTrue(field4.getType().equals(Parm.TYPE.LIST));
		Field field5 = new Field( "field5", Parm.TYPE.STRING);
		assertTrue("field5".equals(field5.getName()));
		assertTrue(field5.getType().equals(Parm.TYPE.STRING));

	}

	@Test
	public void Field2Test() {
		Field field6 = new Field( );
		assertTrue(field6.getName().equals("FIELD1"));
		assertTrue(field6.getType().equals(Parm.TYPE.STRING));
	}

	@Test 
	public void getNameTest() {
		Field f1 = new Field( "f1", Parm.TYPE.LIST);
		assertTrue("f1".equals(f1.getName()));
		Field f2 = new Field( "f2", Parm.TYPE.STRING);
		assertTrue("f2".equals(f2.getName()));
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			Field f3 = new Field( "123", Parm.TYPE.STRING);
			assertTrue(f3.getType().equals(Parm.TYPE.STRING));
		    });
		String expectedMessage = "Illegal variable name[123]";
		String actualMessage = exception.getMessage();
	    assertTrue(actualMessage.contains(expectedMessage));
		Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
			Field f4 = new Field( "abc", null);
			assertTrue(f4.getType().equals(Parm.TYPE.STRING));
		    });
		String expectedMessage1 = "Field type value can't be null";
		String actualMessage1 = exception1.getMessage();
	    assertTrue(actualMessage1.contains(expectedMessage1));

	}

	@Test
	public void getTypeTest(){
		Field field1 = new Field( "field1", Parm.TYPE.BOOLEAN);
		Field field2 = new Field( "field2", Parm.TYPE.DATE);
		Field field3 = new Field( "field3", Parm.TYPE.INTEGER);
		Field field4 = new Field( "field4", Parm.TYPE.LIST);
		Field field5 = new Field( "field5", Parm.TYPE.STRING);
		assertTrue(field1.getType().equals(Parm.TYPE.BOOLEAN));
		assertTrue(field2.getType().equals(Parm.TYPE.DATE));
		assertTrue(field3.getType().equals(Parm.TYPE.INTEGER));
		assertTrue(field4.getType().equals(Parm.TYPE.LIST));
		assertTrue(field5.getType().equals(Parm.TYPE.STRING));
	}

	@Test
	public void equalsTest(){
		Field field10 = new Field( "field10", Parm.TYPE.BOOLEAN);
		Field field11 = new Field( "field11", Parm.TYPE.BOOLEAN);
		Field field12 = new Field( "field10", Parm.TYPE.BOOLEAN);
		assertEquals(field10, field10);
		assertNotEquals(field10, field11);
		assertEquals( field10, field12);
		Field field13 = new Field( "field13", Parm.TYPE.BOOLEAN);
		Field field14 = new Field( "field13", Parm.TYPE.INTEGER);
		assertNotEquals(field13, field14);
		assertNotEquals(field13, null);
	}

	@Test
	public void hashCodeTest() {
		Field field15 = new Field( "field15", Parm.TYPE.STRING);
		assertTrue( field15.hashCode() != 0);
		Field field16 = new Field( "field16", Parm.TYPE.STRING);
		assertTrue( field15.hashCode() != field16.hashCode());		

	}

	@Test 
	public void toStringTest() {
		Field field20 = new Field( "field20", Parm.TYPE.DATE);		
		assertEquals(field20.toString(), "Field{ name[field20] type[DATE]}");
	}

	@Test 
	public void compareToTest() {
		Field field21 = new Field( "field21", Parm.TYPE.DATE);		
		Field field22 = new Field( "field22", Parm.TYPE.INTEGER);		
		Field field23 = new Field( "field23", Parm.TYPE.BOOLEAN);		
		assertTrue(field21.compareTo(field21) == 0);
		assertTrue(field21.compareTo(field22) < 0);
		assertTrue(field23.compareTo(field21) > 0);
	}

	

}
