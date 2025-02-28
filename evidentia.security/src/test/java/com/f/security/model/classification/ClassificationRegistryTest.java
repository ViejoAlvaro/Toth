package com.f.security.model.classification;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.f.basic.model.Parm;

/**
 * Tests the registry of available classification schemas
 */
public class ClassificationRegistryTest {


	private static ClassificationRegistry theRegistry;
	static {
		theRegistry = ClassificationRegistry.getInstance();
	}

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("))) Testing ClassificationRegistry");
		theRegistry.clear();
	}


	@Test 
	public void ClassificationRegistryTest1() {
		ClassificationRegistry registry1 = ClassificationRegistry.getInstance();
		ClassificationRegistry registry2 = ClassificationRegistry.getInstance();
		assertEquals( theRegistry, registry1);
		assertEquals( theRegistry, registry2);
	}

	@Test
	public void addClassificationTest() {
		Classification classification1 = new Classification (Parm.ADMIN_SECURITY_LEVEL, "TRD");
		theRegistry.addClassification(classification1);
		ClassificationRegistry.getInstance().addClassification(classification1);
		Classification classification2 = new Classification (Parm.ADMIN_SECURITY_LEVEL, "MENUS");
		theRegistry.addClassification(classification2);
		Classification c1 = theRegistry.getClassification("TRD");
		assertEquals(c1, classification1);
		Classification c2 = theRegistry.getClassification("MENUS");
		assertEquals(c2, classification2);
		assertNotEquals(c1,c2);
	}

	@Test
	public void getClassificationTest() {
		Classification classification3 = new Classification (Parm.ADMIN_SECURITY_LEVEL, "TRD2");
		theRegistry.addClassification(classification3);
		ClassificationRegistry.getInstance().addClassification(classification3);
		Classification classification4 = new Classification (Parm.ADMIN_SECURITY_LEVEL, "MENUS2");
		theRegistry.addClassification(classification4);
		Classification c3 = theRegistry.getClassification("TRD2");
		assertEquals(c3, classification3);
		Classification c4 = theRegistry.getClassification("MENUS2");
		assertEquals(c4, classification4);
		assertNotEquals(c3,c4);
	}

	@Test
	public void equalsTest() {
		assertEquals(theRegistry, theRegistry);
		ClassificationRegistry registry1 = ClassificationRegistry.getInstance();
		ClassificationRegistry registry2 = ClassificationRegistry.getInstance();
		assertEquals( theRegistry, registry1);
		assertEquals( theRegistry, registry2);

	}

	@Test
	public void hashCodeTest() {
		assertFalse( theRegistry.hashCode() == 0);
		ClassificationRegistry registry2 = ClassificationRegistry.getInstance();
		assertTrue( theRegistry.hashCode() == registry2.hashCode());
	}

	@Test
	public void toStringTest() {
		assertEquals(theRegistry.toString(), "ClassificationRegistry{MENUS MENUS2 TRD TRD2 }");

	}


}
