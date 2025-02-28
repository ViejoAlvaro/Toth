package com.f.security.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.f.basic.model.Identity;
import com.f.basic.model.Parm;
import com.f.security.model.classification.Branch;
import com.f.security.model.classification.Classification;
import com.f.security.model.classification.ClassificationRegistry;
import com.f.security.model.classification.Leaf;
import com.f.security.model.classification.ProcBranch;
import com.f.security.model.classification.ProcLeaf;
import com.f.security.model.classification.ProcedureObject;
import com.f.security.model.object.DataObject;
import com.f.security.model.xml.RoleFromXML;
import com.f.security.model.xml.RoleToXML;

public class RoleTest {

	private static Role            theRole;
	private static Rights          thePerformRights;
	private static Rights          theDataRights;
	private static Classification  dataClassification;
	private static Classification  procClassification;
	private static ClassificationRegistry theClassificationRegistry;
	private static Branch          theBranch;
	private static Branch          thePBranch;
	private static Leaf            theLeaf;
	private static Leaf            thePLeaf;
	private static Leaf            theProc;
	static {
		Identity.setId(0L);
		theClassificationRegistry = ClassificationRegistry.getInstance();
		dataClassification = new Classification (Parm.ADMIN_SECURITY_LEVEL, "THE_CLASSIFICATION");
		theClassificationRegistry.addClassification(dataClassification);
		Branch branch1 = new Branch (3);
		dataClassification.addBranch(branch1, dataClassification.getRoot());
		Branch branch2 = new Branch (3);
		dataClassification.addBranch(branch2, branch1);
		Branch branch3 = new Branch (3);
		dataClassification.addBranch(branch3, branch1);
		Branch branch4 = new Branch (3);
		dataClassification.addBranch(branch4, branch3);
		Branch branch5 = new Branch (3);
		dataClassification.addBranch(branch5, branch3);
		theBranch = new Branch(3);
		dataClassification.addBranch(theBranch, branch5);
		Leaf leaf2 = new Leaf (3);
		dataClassification.addLeaf(leaf2, branch1);
		Leaf leaf3 = new Leaf (3);
		dataClassification.addLeaf(leaf3, branch1);
		Leaf leaf4 = new Leaf (3);
		dataClassification.addLeaf(leaf4, branch3);
		Leaf leaf5 = new Leaf (3);
		dataClassification.addLeaf(leaf5, branch3);
		theLeaf = new Leaf(3);
		dataClassification.addLeaf( theLeaf, branch5);
		theDataRights     = new Rights(Parm.ADMIN_SECURITY_LEVEL, dataClassification);

		Identity.setId(200L);
		procClassification = new Classification (Parm.ADMIN_SECURITY_LEVEL, "THE_procClassification");
		theClassificationRegistry.addClassification(procClassification);
		ProcBranch pBranch1 = new ProcBranch (3);
		procClassification.addBranch(pBranch1, procClassification.getRoot());
		ProcBranch pBranch2 = new ProcBranch (3);
		procClassification.addBranch(pBranch2, pBranch1);
		ProcBranch pBranch3 = new ProcBranch (3);
		procClassification.addBranch(pBranch3, pBranch1);
		ProcBranch pBranch4 = new ProcBranch (3);
		procClassification.addBranch(pBranch4, pBranch3);
		ProcBranch pBranch5 = new ProcBranch (3);
		procClassification.addBranch(pBranch5, pBranch3);
		thePBranch = new ProcBranch(3);
		procClassification.addBranch(thePBranch, pBranch5);
		ProcLeaf pLeaf2 = new ProcLeaf (3);
		procClassification.addLeaf(pLeaf2, pBranch1);
		ProcLeaf pLeaf3 = new ProcLeaf (3);
		procClassification.addLeaf(pLeaf3, pBranch1);
		ProcLeaf pLeaf4 = new ProcLeaf (3);
		procClassification.addLeaf(pLeaf4, pBranch3);
		ProcLeaf pLeaf5 = new ProcLeaf (3);
		procClassification.addLeaf(pLeaf5, pBranch3);
		thePLeaf = new ProcLeaf(3);
		procClassification.addLeaf( thePLeaf, pBranch5);
		thePerformRights     = new Rights(Parm.ADMIN_SECURITY_LEVEL, procClassification);

		Identity.setId(5000L);
		ProcedureObject proc1 = new ProcedureObject(1);
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			thePerformRights.grant(proc1);
		});
		String expectedMessage = "The classification schema does not contain the object[5001]";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));

		thePerformRights.grant(thePLeaf);
		thePerformRights.grant(pBranch3);
		thePerformRights.grant(pLeaf5);

		Identity.setId(6000L);
		theRole           = new Role ("THE_ROLE",  theDataRights, thePerformRights);
	}


	@BeforeAll
	public static void beforeAll() {
		System.out.println("))) Testing Role");
		Identity.setId(0L);
	}
	
	
	@Test
	public void roleTest1() {
		Role role1 = new Role("THE_ROLE", theDataRights, thePerformRights);
		assertNotEquals(role1, theRole);
		Role role2 = role1;
		assertEquals(role1, role2);
		
	}
	
	
	@Test
	public void roleTest2() {
		Role role50 = new Role("ROLE50", theDataRights, thePerformRights);
		Role.Exporter roleToXMLexporter = new RoleToXML();
		String actual = (String)role50.export(roleToXMLexporter);
		RoleFromXML roleImporter  = new RoleFromXML( actual ); 
		Role role52 = new Role(roleImporter);
		String expected = (String)role52.export(roleToXMLexporter);
		assertEquals(actual, expected);
		assertEquals(role50, role52);		
		
	}
	
	
	@Test
	public void getIdTest() {
		Identity.setId(1005L);
		Role role100 = new Role("THE_ROLE", theDataRights, thePerformRights);
		assertEquals(1006L, role100.getId());		
	}
	

	@Test
	public void getNameTest() {
		theRole.setName("THE_ROLE");
		assertEquals("the_role", theRole.getName());    
	}

	@Test
	public void setNameTest() {
		theRole.setName("the_role_new");
		assertEquals("the_role_new", theRole.getName());    
	}


	@Test
	public void isNamedTest() {
		theRole.setName("the_role1");
		assertTrue(theRole.isNamed("the_role1"));
		assertTrue(theRole.isNamed("THE_ROLE1"));
		assertFalse( theRole.isNamed("other_role"));
	}


	@Test 
	void getGuardTest() {
		assertEquals(6001L, theRole.getGuard().getId());
		assertEquals(Parm.ADMIN_SECURITY_LEVEL, theRole.getGuard().getSecurityLevel());
	}


	@Test 
	void setGuardTest() {
		theRole.setGuard(new DataObject(20001, Parm.MAX_SECURITY_LEVEL));
		assertEquals(20001L, theRole.getGuard().getId());
		assertEquals(Parm.MAX_SECURITY_LEVEL, theRole.getGuard().getSecurityLevel());
	}


	@Test 
	void setDataRightsTest() {
		Identity.setId(17000L);
		Role  role20 = new Role("ROLE20", theDataRights, thePerformRights);
		Classification dClassification = new Classification (Parm.ADMIN_SECURITY_LEVEL, "A_CLASSIFICATION");
		theClassificationRegistry.addClassification(dClassification);
		Branch branch100 = new Branch (3);
		dClassification.addBranch(branch100, dClassification.getRoot());
		Rights aDataRights = new Rights(Parm.ADMIN_SECURITY_LEVEL, dClassification);		
		Role  role40 = new Role("ROLE20", aDataRights, thePerformRights);
		assertNotEquals(role20, role40);
		DataObject guard = role20.getGuard();
		role40.setGuard(guard);
		role40.setDataRights(theDataRights);
		assertEquals(role20, role40);
		
	}


	@Test 
	void setPerformRightsTest() {
		Identity.setId(17000L);
		Role  role30 = new Role("A_ROLE", theDataRights, null);
		DataObject guard = role30.getGuard();
		Role  role40 = new Role("ROLE40", theDataRights, thePerformRights);
		role40.setGuard(guard);
		assertNotEquals(role30,role40);
		role40.setName("A_ROLE");
		role30.setPerformRights(thePerformRights);
		assertEquals(role30,role40);
	}


	@Test
	public void grantDataAccessTest() {
		Identity.setId(200L);
		Leaf leaf10 = new Leaf(0);
		dataClassification.addLeaf(leaf10, theBranch);
		theDataRights.grant(leaf10);
		assertTrue (theRole.rightsAllowsAccess(leaf10));
		assertFalse(theRole.rightsAllowsAccess(theLeaf));     
	}


	@Test
	public void revokeDataAccessTest() {
		Identity.setId(300L);
		Leaf leaf11 = new Leaf(1);
		dataClassification.addLeaf(leaf11, theBranch);
		theRole.grantDataAccess(leaf11);
		assertTrue (theRole.rightsAllowsAccess(leaf11));
		assertFalse(theRole.rightsAllowsAccess(theLeaf));  
		theRole.revokeDataAccess(leaf11);
		assertFalse(theRole.rightsAllowsAccess(leaf11));
	}


	@Test
	public void grantPerformAccessTest() {
		Identity.setId(750L);
		theProc = new Leaf(3);
		assertFalse(theRole.rightsAllowsPerform(theProc));
		Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
			thePerformRights.grant(theProc);
		});
		String expectedMessage1 = "The classification schema does not contain the object[751]";
		String actualMessage1 = exception1.getMessage();
		assertTrue(actualMessage1.contains(expectedMessage1));

		procClassification.addLeaf(theProc, thePBranch);
		theRole.grantPerformAccess(theProc);
		assertTrue(theRole.rightsAllowsPerform(theProc));
	}


	@Test
	public void revokePerformAccessTest() {
		Identity.setId(200L);
		Leaf proc20 = new Leaf(3);
		procClassification.addLeaf(proc20, thePBranch);
		assertFalse(theRole.rightsAllowsPerform(proc20));
		theRole.grantPerformAccess(proc20);
		assertTrue(theRole.rightsAllowsPerform(proc20));
		theRole.revokePerformAccess(proc20);
		assertFalse(theRole.rightsAllowsAccess(proc20));
	}


	@Test
	public void rightsAllowsAccessTest() {
		Identity.setId(400L);
		Leaf leaf20 = new Leaf(1);
		dataClassification.addLeaf(leaf20, theBranch);
		assertFalse(theRole.rightsAllowsAccess(leaf20));
		theRole.grantDataAccess(leaf20);
		assertTrue (theRole.rightsAllowsAccess(leaf20));
		assertFalse(theRole.rightsAllowsAccess(theLeaf));  
		theRole.revokeDataAccess(leaf20);
		assertFalse(theRole.rightsAllowsAccess(leaf20));
	}


	@Test
	public void rightsAllowsPerformTest() {
		Identity.setId(500L);
		Leaf proc20 = new Leaf(5);
		assertFalse(theRole.rightsAllowsPerform(proc20));
		procClassification.addLeaf(proc20, thePBranch);
		theRole.grantPerformAccess(proc20);
		assertTrue(theRole.rightsAllowsPerform(proc20));
		theRole.revokePerformAccess(proc20);
		assertFalse(theRole.rightsAllowsPerform(proc20));
	}
	
	
	@Test
	public void exportTest() {
		Role role5 = new Role("ROLE5", theDataRights, thePerformRights);
		Role.Exporter roleToXMLexporter = new RoleToXML();
		String actual = (String)role5.export(roleToXMLexporter);
		String expected = "<Role name=\"role5\">\n"
				+ "   <Toth id=\"202\" level=\"3\"/>\n"
				+ "   <DataRights  classification=\"THE_CLASSIFICATION\">\n"
				+ "201/0 \n"
				+ "   </DataRights>\n"
				+ "   <ProcRights  classification=\"THE_PROCCLASSIFICATION\">\n"
				+ "213/3 205/3 212/3 751/3 \n"
				+ "   </ProcRights>\n"
				+ "</Role>\n";
		assertEquals(actual, expected);
		
	}


	@Test
	public void acceptsTest() {
		//Tested in UserTest
	}


	@Test
	public void equalsTest(){
		assertTrue(theRole.equals(theRole));
		Role otherRole = new Role ("THE_ROLE",  theDataRights, thePerformRights);
		assertFalse(theRole.equals( otherRole));
		Role role2 = new Role ("ROLE2",  theDataRights, thePerformRights);
		assertFalse(otherRole.equals(role2));
		Role role3 = role2;
		assertTrue( role3.equals(role2));
	}


	@Test
	public void hashCodeTest() {
		Identity.setId(600L);
		Role role3 = new Role ("ROLE3",  theDataRights, thePerformRights);
		assertTrue( role3.hashCode() != 0);
		Role role4 = theRole;
		assertTrue ( role4.hashCode() == theRole.hashCode());
		assertFalse( role3.hashCode() == role4.hashCode());      
	}


	@Test
	public void toStringTest() {
		Identity.setId(700L);
		Role aRole = new Role ("A_ROLE",  theDataRights, thePerformRights);
		assertTrue(aRole.toString().equals("Role{ name[a_role] guard{ id[701] level[3]} dataRights#=1 performRights#=4}"));

	}


	@Test 
	public void compareToTest(){
		Role otherRole = new Role ("OTHER_ROLE",  theDataRights, thePerformRights);
		assertTrue (otherRole.compareTo(otherRole) == 0);
		Role aRole     = new Role ("A_ROLE",      theDataRights, thePerformRights);
		assertTrue( otherRole.compareTo(aRole) > 0);
		Role bRole     = new Role ("B_ROLE",      theDataRights, thePerformRights);
		assertTrue( aRole.compareTo(bRole) < 0);
	}



}
