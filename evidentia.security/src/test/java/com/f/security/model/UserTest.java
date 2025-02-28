package com.f.security.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.f.basic.model.Identity;
import com.f.basic.model.Parm;
import com.f.metadata.model.EnumField;
import com.f.metadata.model.EnumRegistry;
import com.f.metadata.model.EnumType;
import com.f.metadata.model.Field;
import com.f.metadata.model.Metadata;
import com.f.metadata.model.Schema;
import com.f.metadata.model.SchemaRegistry;
import com.f.metadata.model.Var;
import com.f.security.model.classification.Branch;
import com.f.security.model.classification.Classification;
import com.f.security.model.classification.Leaf;
import com.f.security.model.classification.ProcBranch;
import com.f.security.model.classification.ProcLeaf;
import com.f.security.model.classification.ProcedureObject;
import com.f.security.model.object.DataObject;
import com.f.security.model.object.TothObject;
import com.f.security.model.xml.UserFromXML;
import com.f.security.model.xml.UserToXML;

public class UserTest {

	// Security structure
	private static RoleRegistry     theRoleRegistry;
	private static Role             requiredRole1;
	private static Role             enablingRole1;
	private static Set<Role>        theRequiredRoles;
	private static Set<Role>        theEnablingRoles;
	private static Rights           thePerformRights1;
	private static Rights           thePerformRights2;
	private static Rights           theDataRights;
	private static Classification   theClassification;
	private static Branch           theBranch;
	private static Leaf             theLeaf;
	private static ProcBranch       thePBranch;
	private static ProcLeaf         thePLeaf;
	private static ProcLeaf         theProc;

	// Metadata structure
	private static Metadata         theMeta;
	private static Schema           theSchema;
	private static EnumType         theType;
	private static EnumRegistry     theRegistry;
	private static List<String>     theValues;
	private static Set<Field>       theFields;
	private static Set<String>      theRange; 
	private static List<Var>        theVars;
	private static SchemaRegistry   theSchemaRegistry;



	static {
		// Set up the data classification hierarchy and build the data rights
		Identity.setId(0L);
		theClassification = new Classification (Parm.ADMIN_SECURITY_LEVEL, "THE_CLASSIFICATION");
		Branch branch1    = new Branch (3);
		theClassification.addBranch(branch1, theClassification.getRoot());
		Branch branch2    = new Branch (3);
		theClassification.addBranch(branch2, branch1);
		Branch branch3    = new Branch (3);
		theClassification.addBranch(branch3, branch1);
		Branch branch4    = new Branch (3);
		theClassification.addBranch(branch4, branch3);
		Branch branch5    = new Branch (3);
		theClassification.addBranch(branch5, branch3);
		theBranch         = new Branch(3);
		theClassification.addBranch(theBranch, branch5);
		Leaf leaf2        = new Leaf (3);
		theClassification.addLeaf(leaf2, branch1);
		Leaf leaf3        = new Leaf (3);
		theClassification.addLeaf(leaf3, branch1);
		Leaf leaf4        = new Leaf (3);
		theClassification.addLeaf(leaf4, branch3);
		Leaf leaf5        = new Leaf (3);
		theClassification.addLeaf(leaf5, branch3);
		theBranch         = new Branch(2);
		theClassification.addBranch(theBranch, branch1);
		theLeaf           = new Leaf(3);
		theClassification.addLeaf( theLeaf, branch5);
		theDataRights     = new Rights(Parm.ADMIN_SECURITY_LEVEL, theClassification);
		theDataRights.grant(theBranch);
		theDataRights.grant(theLeaf);

		// Set up the perform rights
		 
	      Identity.setId(200L);
	      Classification classification2 = new Classification (Parm.ADMIN_SECURITY_LEVEL, "THE_CLASSIFICATION2");
	      Branch pBranch1 = new ProcBranch (3);
	      classification2.addBranch(pBranch1, classification2.getRoot());
	      Branch pBranch2 = new ProcBranch (3);
	      classification2.addBranch(pBranch2, pBranch1);
	      Branch pBranch3 = new ProcBranch (3);
	      classification2.addBranch(pBranch3, pBranch1);
	      Branch pBranch4 = new ProcBranch (3);
	      classification2.addBranch(pBranch4, pBranch3);
	      Branch pBranch5 = new ProcBranch (3);
	      classification2.addBranch(pBranch5, pBranch3);
	      thePBranch = new ProcBranch(3);
	      classification2.addBranch(thePBranch, pBranch5);
	      Leaf pLeaf2 = new Leaf (3);
	      classification2.addLeaf(pLeaf2, pBranch1);
	      Leaf pLeaf3 = new Leaf (3);
	      classification2.addLeaf(pLeaf3, pBranch1);
	      Leaf pLeaf4 = new Leaf (3);
	      classification2.addLeaf(pLeaf4, pBranch3);
	      Leaf pLeaf5 = new Leaf (3);
	      classification2.addLeaf(pLeaf5, pBranch3);
	      thePLeaf = new ProcLeaf(3);
	      theProc  = thePLeaf;
	      classification2.addLeaf( thePLeaf, pBranch5);
	      thePerformRights2  = new Rights(Parm.ADMIN_SECURITY_LEVEL, classification2);


		// Setup roles
		Identity.setId(100L);
		theRoleRegistry  = RoleRegistry.getInstance();
		requiredRole1    = new Role ("REQUIRED_ROLE1",  theDataRights, null);
		enablingRole1    = new Role ("ENABLING_ROLE1",  theDataRights, thePerformRights2);
		theRequiredRoles = new TreeSet<Role>();
		theEnablingRoles = new TreeSet<Role>();
		theRequiredRoles.add(requiredRole1);
		theEnablingRoles.add(enablingRole1);
		theRoleRegistry.addRole(requiredRole1);
		theRoleRegistry.addRole(enablingRole1);

		// Setup metadata    
		theSchemaRegistry = SchemaRegistry.getInstance();
		theRegistry       = EnumRegistry.getInstance();
		theRegistry.clear();
		theRange = new TreeSet<String>();
		theRange.add("A");
		theRange.add("B");
		theRange.add("C");
		theRange.add("D");
		theRange.add("E");
		theRange.add("1");
		theRange.add("2");
		theRange.add("3");
		theType = new EnumType ( "THE_TYPE", theRange);
		theRegistry.addType(theType);

		Field field1 = new Field( "field1", Parm.TYPE.BOOLEAN);
		Field field2 = new Field( "field2", Parm.TYPE.DATE);
		Field field3 = new Field( "field3", Parm.TYPE.INTEGER);
		Field field4 = new EnumField( "field4", "THE_TYPE");
		Field field5 = new Field( "field5", Parm.TYPE.STRING);
		theFields    = new TreeSet<Field>();
		theFields.add(field1);
		theFields.add(field2);
		theFields.add(field3);
		theFields.add(field4);
		theFields.add(field5);
		theSchema = new Schema("THE_SCHEMA", theFields);
		theSchemaRegistry.addSchema(theSchema);

		theVars = new ArrayList<Var>();
		theVars.add(new Var(field1, "TRUE"));
		theVars.add(new Var(field2, "2025-02-03T06:03:01.501476700"));
		theVars.add(new Var(field3, "12345"));
		theVars.add(new Var(field4,  "A"));
		theVars.add(new Var(field5, "STRING_VALUE"));


		theValues = new ArrayList<String>();
		theValues.add("TRUE");
		theValues.add("2025-02-03T06:03:01.501476700");
		theValues.add("12345");
		theValues.add( "A");
		theValues.add("STRING_VALUE");      
		theMeta = new Metadata("THE_META",theSchema, theValues);
	}


	@BeforeAll
	public static void beforeAll() {
		System.out.println("))) Testing User");
		Identity.setId(0L);
	}


	@Test
	public void getGuardTest() {
		Identity.setId(600L);
		User anotherUser = new User("USER102", "Pedro", "PicaPiedra", "pedro.picapiedra@gmail.com", Parm.ADMIN_SECURITY_LEVEL, theMeta, theRequiredRoles, theEnablingRoles);
		TothObject guard = anotherUser.getGuard();
		assertTrue(guard.getId() == 601);
		assertTrue(guard.getSecurityLevel() == Parm.ADMIN_SECURITY_LEVEL);
		assertFalse(guard.isPublic());
		assertEquals(guard.toString(),"guard{ id[601] level[3]}");
	}
	
	
	@Test
	public void getIdTest() {
		Identity.setId(9000L);
		User user500 = new User("USER5", "Alvaro", "Lopez", "alvaro.lopez@gmail.com", Parm.MAX_SECURITY_LEVEL, theMeta, theRequiredRoles, theEnablingRoles);
		assertEquals(9001L, user500.getId());
	}


	@Test
	public void getSecurityLevelTest() {
		Identity.setId(700L);
		User user3 = new User("USER103", "Alvaro", "Lopez", "alvaro.lopez@gmail.com", Parm.MAX_SECURITY_LEVEL, theMeta, theRequiredRoles, theEnablingRoles);
		assertTrue( user3.getSecurityLevel() == Parm.MAX_SECURITY_LEVEL);
		user3.setSecurityLevel(Parm.PUBLIC_SECURITY_LEVEL);
		assertTrue(user3.isPublic());
		assertTrue( user3.getSecurityLevel() == Parm.PUBLIC_SECURITY_LEVEL);
	}


	@Test
	public void isPublicTest() {
		Identity.setId(800L);
		User user4 = new User("USER105", "Herman", "Benavides", "herman.benavides@gmail.com", Parm.MAX_SECURITY_LEVEL, theMeta, theRequiredRoles, theEnablingRoles);
		assertFalse(user4.isPublic());
		user4.setSecurityLevel(Parm.PUBLIC_SECURITY_LEVEL);
		assertTrue(user4.isPublic());
		assertTrue( user4.getSecurityLevel() == Parm.PUBLIC_SECURITY_LEVEL);    
	}


	@Test
	public void setSecurityLevelTest() {
		Identity.setId(900L);
		User user5 = new User("USER105", "Francisco", "Santander", "Francisco.Santander@gmail.com", Parm.ADMIN_SECURITY_LEVEL, theMeta, theRequiredRoles, theEnablingRoles);
		assertTrue( user5.getSecurityLevel() == Parm.ADMIN_SECURITY_LEVEL);
		user5.setSecurityLevel(Parm.MAX_SECURITY_LEVEL);
		assertFalse(user5.getGuard().isPublic());
		assertTrue( user5.getSecurityLevel() == Parm.MAX_SECURITY_LEVEL);
	}


	@Test
	public void getMetadata() {
		Identity.setId(1000L);
		User user5 = new User("USER1001", "Simon", "Garfunkel", "Simon.Garfunkel@gmail.com", Parm.MIN_SECURITY_LEVEL, theMeta, theRequiredRoles, theEnablingRoles);
		Metadata meta1 = user5.getMetadata();
		assertEquals(theMeta,meta1);
		assertEquals(meta1.getSchema(), theSchema);
		Iterator<Var> varIter = theMeta.varIterator();
		int i=0;
		while(varIter.hasNext()) {
			Var v = varIter.next();
			assertTrue(v.equals(theVars.get(i)));
			i++;
		}

	}


	@Test
	public void addRequiredRoleTest() {
		Identity.setId(1100L);
		User user5 = new User("USER1101", "Carlos", "Gardel", "Carlos.Gardel@gmail.com", Parm.ADMIN_SECURITY_LEVEL, theMeta, theRequiredRoles, theEnablingRoles);
		Role reqRole2    = new Role ("REQUIRED_ROLE2",  theDataRights, null);
		user5.addRequiredRole(reqRole2);
		Role reqRole =  user5.getRequiredRole("required_role2");
		assertEquals(reqRole, reqRole2);
	}


	@Test
	public void getRequiredRoleTest() {
		Identity.setId(1200L);
		User user6 = new User("USER1201", "Felipe", "Pirela", "Felipe.Pirela@gmail.com", Parm.ADMIN_SECURITY_LEVEL, theMeta, theRequiredRoles, theEnablingRoles);
		Role reqRole = user6.getRequiredRole("REQUIRED_ROLE1");
		assertEquals(reqRole, requiredRole1);
		assertNotEquals(reqRole, enablingRole1);     
	}


	@Test
	public void addEnablingRoleTest() {
		Identity.setId(1300L);
		User user5 = new User("USER1301", "Olga", "Guillot", "Olga.Guillot@gmail.com", Parm.ADMIN_SECURITY_LEVEL, theMeta, theRequiredRoles, theEnablingRoles);
		Role enabRole2    = new Role ("ENABLING_ROLE2",  theDataRights, thePerformRights1);
		user5.addEnablingRole(enabRole2);
		Role enabRole =  user5.getEnablingRole("enabling_role2");
		assertEquals(enabRole, enabRole2);     
	}


	@Test
	public void getEnablingRoleTest() {
		Identity.setId(1400L);
		User user7 = new User("USER1401", "JuanEs", "Aristizabal", "Juanes.Aristizabal@gmail.com", Parm.ADMIN_SECURITY_LEVEL, theMeta, theRequiredRoles, theEnablingRoles);
		Role eRole = new Role ("ENABLING_ROLE5",  theDataRights, thePerformRights2);
		Role enabRole = user7.getEnablingRole("enabling_role1");
		assertEquals(enabRole, enablingRole1);
		assertNotEquals(enabRole, eRole);      
	}


	@Test
	public void removeRequiredRoleTest() {
		Identity.setId(1500L);
		User user8 = new User("USER1501", "Frank", "Sinatra", "Frank.Sinatra@gmail.com", Parm.ADMIN_SECURITY_LEVEL, theMeta, theRequiredRoles, theEnablingRoles);
		Role reqRole = user8.getRequiredRole("required_role1");
		Role enbRole = user8.getEnablingRole("enabling_role1");
		assertNotNull(reqRole);
		assertNotNull(enbRole);
		user8.removeRequiredRole("required_role1");
		Role rrqqRole = user8.getRequiredRole("required_role1");
		assertNull(rrqqRole);
	}



	@Test
	public void requiredRolesIteratorTest() {
		Identity.setId(1800L);
		User user8 = new User("USER1801", "Benny", "Goodman", "Benny.Goodman@gmail.com", Parm.ADMIN_SECURITY_LEVEL, theMeta, theRequiredRoles, theEnablingRoles);
		Iterator<Role> reqIterator = user8.requiredRolesIterator();
		if(reqIterator.hasNext()){
			Role r = reqIterator.next();
			assertEquals(r, requiredRole1);
		}
	}


	@Test
	public void enablingRolesIterator(){
		Identity.setId(3800L);
		User user80 = new User("USER3801", "Alejo", "Duran", "Alejo.Duran@gmail.com", Parm.ADMIN_SECURITY_LEVEL, theMeta, theRequiredRoles, theEnablingRoles);
		Iterator<Role> enabIterator = user80.enablingRolesIterator();
		if(enabIterator.hasNext()){
			Role r = enabIterator.next();
			assertEquals(r, enablingRole1);
		}
	}



	@Test
	public void removeEnablingRoleTest() {
		Identity.setId(1600L);
		User user9   = new User("USER1601", "Nat", "Cole", "Natking.Cole@gmail.com", Parm.ADMIN_SECURITY_LEVEL, theMeta, theRequiredRoles, theEnablingRoles);
		Role reqRole = user9.getRequiredRole("required_role1");
		Role enbRole = user9.getEnablingRole("enabling_role1");
		assertNotNull(reqRole);
		assertNotNull(enbRole);
		user9.removeEnablingRole("enabling_role1");
		Role eennRole = user9.getRequiredRole("enabling_role1");
		assertNull(eennRole);
	}


	@Test
	public void acceptsTest() {
		Identity.setId(1700L);
		User user10 = new User("USER1701", "Jose", "Barros", "Jose.Barros@gmail.com", Parm.ADMIN_SECURITY_LEVEL, theMeta, theRequiredRoles, theEnablingRoles);
		Iterator<Role> reqIter = user10.requiredRolesIterator();
		if(reqIter.hasNext()) {
			Role r = reqIter.next();
			assertFalse(r.accepts(user10));		// The role has to be in a classification
		}

		Iterator<Role> enabIter = user10.enablingRolesIterator();
		if(enabIter.hasNext()) {
			Role r = enabIter.next();
			assertFalse(r.accepts(user10));		// The role is not a PerformObject
		}
		
		assertTrue(theLeaf.accepts(user10));
		assertTrue(theBranch.accepts(user10));
		Leaf leaf200 = new Leaf(3);
		assertFalse( leaf200.accepts(user10));
		Branch branch200 = new Branch(3);
		assertFalse( branch200.accepts(user10));
		assertFalse( theClassification.accepts(user10));
		
		ProcedureObject proc100 = new ProcedureObject(3);
		assertFalse( proc100.accepts(user10));
		Role enablingRole3    = new Role ("ENABLING_ROLE3",  theDataRights, thePerformRights2);
		Set<Role> someRoles   = new TreeSet<Role>();
		someRoles.add(enablingRole3);
		User user11 = new User("USER1101", "JoseA", "Morales", "JoseA.Morales@gmail.com", Parm.ADMIN_SECURITY_LEVEL, theMeta, theRequiredRoles, someRoles);
		assertTrue ( theProc.accepts(user11));

	}


	@Test
	public void getNameTest() {
		Identity.setId(1700L);
		User user10 = new User("USER1701", "Jose", "Barros", "Jose.Barros@gmail.com", Parm.ADMIN_SECURITY_LEVEL, theMeta, theRequiredRoles, theEnablingRoles);
		String userName = user10.getName();
		assertEquals(userName, "Barros Jose");
	}


	@Test
	public void impliesTest() {
		Identity.setId(1800L);
		User user10 = new User("USER1801", "Pacho", "Galan", "Pacho.Galan@gmail.com", Parm.ADMIN_SECURITY_LEVEL, theMeta, theRequiredRoles, theEnablingRoles);
		assertTrue(user10.implies(null));
	}


	@Test
	public void securityLevelAllowsAccessTest() {
		Identity.setId(1900L);
		User user10 = new User("USER1901", "Lucho", "Bermudez", "Lucho.Bermudez@gmail.com", Parm.ADMIN_SECURITY_LEVEL, theMeta, theRequiredRoles, theEnablingRoles);
		TothObject tObj1 = new DataObject(Parm.MAX_SECURITY_LEVEL);
		assertFalse(user10.securityLevelAllowsAccess(tObj1));
		TothObject tObj2 = new DataObject(Parm.ADMIN_SECURITY_LEVEL);
		assertTrue(user10.securityLevelAllowsAccess(tObj2));
		user10.setSecurityLevel(Parm.PUBLIC_SECURITY_LEVEL);
		assertFalse(user10.securityLevelAllowsAccess(tObj1));
		assertFalse(user10.securityLevelAllowsAccess(tObj2));
		TothObject tObj3 = new DataObject(Parm.PUBLIC_SECURITY_LEVEL);
		assertTrue(user10.securityLevelAllowsAccess(tObj3));

	}


	@Test
	public void dataRightsAllowAccessTest() {
		Identity.setId(2000L);
		User user11 = new User("USER2001", "Diana", "Krall", "Diana.Krall@gmail.com", Parm.ADMIN_SECURITY_LEVEL, theMeta, theRequiredRoles, theEnablingRoles);
		Branch  branch1 = new Branch(Parm.PUBLIC_SECURITY_LEVEL);
		Branch  branch2 = new Branch(Parm.ADMIN_SECURITY_LEVEL);
		assertTrue( user11.dataRightsAllowAccess(theBranch));
		assertTrue( user11.dataRightsAllowAccess(theLeaf));
		assertTrue( user11.dataRightsAllowAccess(branch1));
		assertFalse( user11.dataRightsAllowAccess(branch2));
		Leaf leaf1 = new Leaf(Parm.PUBLIC_SECURITY_LEVEL);
		Leaf leaf2 = new Leaf(Parm.ADMIN_SECURITY_LEVEL);
		assertTrue(user11.dataRightsAllowAccess(leaf1));
		assertFalse(user11.dataRightsAllowAccess(leaf2));
	}


	@Test
	public void performRightsAllowAccessTest() {
		Identity.setId(2100L);
		User user11 = new User("USER2101", "Aretha", "Franklin", "Aretha.Franklin@gmail.com", Parm.ADMIN_SECURITY_LEVEL, theMeta, theRequiredRoles, theEnablingRoles);
		ProcedureObject  obj1 = new ProcedureObject(Parm.PUBLIC_SECURITY_LEVEL);
		ProcedureObject  obj2 = new ProcedureObject(Parm.ADMIN_SECURITY_LEVEL);
		assertTrue ( user11.performRightsAllowAccess(obj1));
		assertFalse( user11.performRightsAllowAccess(obj2));
		assertFalse( user11.performRightsAllowAccess(theProc));
		Role role1 = new Role("ROLE1",  theDataRights, thePerformRights2);
        thePerformRights2.grant(theProc);
		user11.addEnablingRole(role1);
		assertTrue ( user11.performRightsAllowAccess(theProc));
	}

	@Test
	public void exportTest() {
		Identity.setId(3000L);
		User user11 = new User("USER3001", "Andrea", "Boccelli", "Andrea.Boccelli@gmail.com", Parm.ADMIN_SECURITY_LEVEL, theMeta, theRequiredRoles, theEnablingRoles);
		User.Exporter userToXML = new UserToXML();  
		String actual = (String)user11.export(userToXML);
        String expected = "<User code=\"USER3001\" lastName=\"Boccelli\" firstName=\"Andrea\" email=\"Andrea.Boccelli@gmail.com\">\n"
        		+ "   <Toth id=\"3001\" level=\"3\"/>\n"
        		+ "   <requiredRoles>\n"
        		+ "      <requiredRole r=\"required_role1\" />\n"
        		+ "   </requiredRoles>\n"
        		+ "   <enablingRoles>\n"
        		+ "      <enablingRole e=\"enabling_role1\" />\n"
        		+ "   </enablingRoles>\n"
        		+ "   <metadata name=\"THE_META\" schema=\"THE_SCHEMA\">\n"
        		+ "      <values>\n"
        		+ "         <value v=\"TRUE\"/>\n"
        		+ "         <value v=\"2025-02-03T06:03:01.501476700\"/>\n"
        		+ "         <value v=\"12345\"/>\n"
        		+ "         <value v=\"A\"/>\n"
        		+ "         <value v=\"STRING_VALUE\"/>\n"
        		+ "      </values>\n"
        		+ "   </metadata>\n"
        		+ "</User>\n";
		assertEquals(expected, actual); 
	}
	
	
	@Test  void  importTest() {
		Identity.setId(5000L);
		User user51 = new User("USER5001", "Luciano", "Pavaroti", "Luciano.Pavaroti@gmail.com", Parm.ADMIN_SECURITY_LEVEL, theMeta, theRequiredRoles, theEnablingRoles);
		User.Exporter userToXML = new UserToXML();  
		String actual = (String)user51.export(userToXML);
		UserFromXML userImporter  = new UserFromXML( actual ); 
		User user52 = new User(userImporter);
		String expected = (String)user52.export(userToXML);
		assertEquals(actual, expected);
		assertEquals(user51, user52);		
	}


	@Test
	public void equalsTest(){
		Identity.setId(2200L);
		User user12 = new User("USER2201", "Pablo", "Milanes", "Pablo.Milanes@gmail.com", Parm.ADMIN_SECURITY_LEVEL, theMeta, theRequiredRoles, theEnablingRoles);
		User user13 = new User("USER2202", "Ellis", "Regina",  "Ellis.Regina@gmail.com",  Parm.ADMIN_SECURITY_LEVEL, theMeta, theRequiredRoles, theEnablingRoles);
		assertEquals(user12, user12);
		assertNotEquals(user12, user13);
	}


	@Test
	public void hashCodeTest(){
		Identity.setId(2300L);
		User user13 = new User("USER2201", "Carlos", "Vives",  "Carlos.Vives@gmail.com",   Parm.ADMIN_SECURITY_LEVEL, theMeta, theRequiredRoles, theEnablingRoles);
		User user14 = new User("USER2202", "Andy", "Williams", "Andy.Williams@gmail.com",  Parm.ADMIN_SECURITY_LEVEL, theMeta, theRequiredRoles, theEnablingRoles);
		assertTrue( user13.hashCode() != 0);
		assertFalse( user13.hashCode() == user14.hashCode());
	}


	@Test
	public void toStringTest() {
		Identity.setId(2400L);
		User user15 = new User("USER2201", "Pablo", "Milanes", "Pablo.Milanes@gmail.com", Parm.ADMIN_SECURITY_LEVEL, theMeta, theRequiredRoles, theEnablingRoles);
		assertEquals(user15.toString(),"User{id[USER2201] guard{ id[2401] level[3]} first name[Pablo] last name[Pablo] email[Pablo.Milanes@gmail.com] access level[3]  required roles[required_role1 ] enablingRoles[ enabling_role1] Metadata{ name[THE_META] schema[THE_SCHEMA] values#=5}}");
	}


	@Test
	public void compareToTest(){
		Identity.setId(2500L);
		User user16 = new User("USER2501", "Elvis", "Presley",  "Elvis.Presley@gmail.com",   Parm.ADMIN_SECURITY_LEVEL, theMeta, theRequiredRoles, theEnablingRoles);
		User user17 = new User("USER2502", "Neil",  "Young", "Neil.Young@gmail.com",  Parm.ADMIN_SECURITY_LEVEL, theMeta, theRequiredRoles, theEnablingRoles);
		assertTrue( user17.compareTo(user16) > 0);
	}

}
