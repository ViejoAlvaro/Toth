package com.f.metadata.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BooleanValueTest {
	
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("))) Testing BooleanValue");
	}

	
	@Test
	public void BooleanValueTest1() {
		BooleanValue bool1 = new BooleanValue(true);
		assertTrue( bool1.getBooleanValue());
		BooleanValue bool2 = new BooleanValue(false);
        assertFalse( bool2.getBooleanValue());
	}
	
	
	@Test
	public void BooleanValueTest2() {
		BooleanValue bool1 = new BooleanValue("TRUE");
		assertTrue(  bool1.getBooleanValue());
		BooleanValue bool2 = new BooleanValue("FALSE");
        assertFalse( bool2.getBooleanValue());
		BooleanValue bool3 = new BooleanValue("true");
		assertTrue(  bool3.getBooleanValue());
		BooleanValue bool4 = new BooleanValue("false");
        assertFalse( bool4.getBooleanValue());
		BooleanValue bool5 = new BooleanValue("XXXX");
		assertFalse( bool5.getBooleanValue());
		BooleanValue bool6 = new BooleanValue(null);
		assertFalse( bool6.getBooleanValue());
	}
	
	
	@Test
	public  void   getBooleanValueTest() {
		BooleanValue bool1 = new BooleanValue("TRUE");
		assertTrue(  bool1.getBooleanValue());
		BooleanValue bool2 = new BooleanValue("FALSE");
        assertFalse( bool2.getBooleanValue());
		BooleanValue bool3 = new BooleanValue("true");
		assertTrue(  bool3.getBooleanValue());
		BooleanValue bool4 = new BooleanValue("false");
        assertFalse( bool4.getBooleanValue());
	}		

}
