package com.f.basic.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UTILTest {
	
	static {
		Identity.setId(100L);
	}
	
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("))) Testing UTIL");
	}
	
	@Test
	public void isValidNameTest() {
		assertTrue (UTIL.isValidName("UnidentificadorMuyLargoooooooooooooooaaaaaaaaaaaaaaaaaaaaaa"));
		assertFalse(UTIL.isValidName("UnidentificadorMuyLargoooooooooooooooaaaaaaaaaaaaaaaaaaaaa a"));

		assertTrue (UTIL.isValidName("AnIdentifier"));
		assertTrue (UTIL.isValidName("AnIdentifier1"));
		assertTrue (UTIL.isValidName("AnIdentifier_123"));
		assertTrue (UTIL.isValidName("$AnIdentifier"));
		assertTrue (UTIL.isValidName("AnIdentifier$"));
		assertTrue (UTIL.isValidName("$AnIdentifier_$$"));
		
		assertFalse( UTIL.isValidName(null));
		assertFalse( UTIL.isValidName(""));
		assertFalse( UTIL.isValidName("   "));
		assertFalse( UTIL.isValidName(" Identifier"));
		assertFalse( UTIL.isValidName("Identifier "));
		assertFalse( UTIL.isValidName("I dentifier"));
		assertFalse( UTIL.isValidName("123_Identifier"));
		assertFalse( UTIL.isValidName("I-dentifier"));
		assertFalse( UTIL.isValidName("I2_bcd*"));
		assertFalse( UTIL.isValidName("Identifier;"));
		assertFalse( UTIL.isValidName("Identifier-"));
		assertFalse( UTIL.isValidName("Identifier:"));
		assertFalse( UTIL.isValidName("Identifier\t"));
		assertFalse( UTIL.isValidName("Identifier\n"));
		assertFalse( UTIL.isValidName("Identifier\r"));

	}

	
	@Test
	public void indentStringTest( ) {
		assertEquals( UTIL.indentString(5), "               ");
		assertEquals( UTIL.indentString(4), "            ");
		assertEquals( UTIL.indentString(3), "         ");
		assertEquals( UTIL.indentString(2), "      ");
		assertEquals( UTIL.indentString(1), "   ");
	}
	
	
	@Test
	public void isValidStringValueTest() {
		assertTrue( UTIL.isValidStringValue("12345"));
		assertTrue( UTIL.isValidStringValue("ABCD"));
		assertTrue( UTIL.isValidStringValue("abcd"));
		assertTrue( UTIL.isValidStringValue(".,;/?"));
		assertFalse(UTIL.isValidStringValue("<xyz>"));
	}

}
