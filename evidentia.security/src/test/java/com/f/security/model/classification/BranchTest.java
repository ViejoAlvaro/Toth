package com.f.security.model.classification;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.f.basic.model.Identity;
import com.f.basic.model.Parm;

public class BranchTest {

	private static Branch  theBranch;
	static {
		Identity.setId(0L);
		theBranch = new Branch( Parm.ADMIN_SECURITY_LEVEL);
	}


	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("))) Testing Branch");
		Identity.setId(200L);
	}
	
	@Test
	public void branchTest1() {
		Identity.setId(140L);
		Branch branch1 = new Branch(3);
		assertEquals(141, branch1.getId());
	}

	@Test
	public void getGuardTest() {
		Identity.setId(10L);
		Branch aBranch = new Branch(3);
		assertEquals(aBranch.toString(), "Branch{guard{ id[11] level[3]} parent[nodeIsroot] children #=0}");
	}
	
	@Test	
	public void getIdTest() {
		Identity.setId(100L);
		Branch aBranch = new Branch(3);
		assertEquals(101, aBranch.getId());
	}
	
	
	@Test
	public void getSecurityLevelTest() {
		Identity.setId(100L);
		Branch aBranch = new Branch(3);
		assertEquals(3, aBranch.getSecurityLevel());
	}
	
	
	public void getComponentsTest()  {
		Identity.setId(100L);
		Branch branch1 = new Branch(4);
		Branch branch2 = new Branch(3);
		branch2.addToParent(branch1);
		Leaf leaf1 = new Leaf(4);
		leaf1.addToParent(branch1);
		Set<Component> theComponents = branch1.getComponents();
		assertTrue(theComponents.contains(branch2));
		assertTrue(theComponents.contains(leaf1));
	}

	
	@Test
	public void getParentTest(){
		Identity.setId(100L);
		Branch branch1 = new Branch(4);
		Branch branch2 = new Branch(3);
		branch2.addToParent(branch1);
		assertEquals(branch1, branch2.getParent());
	}
	
	
	@Test
	public void getRootTest() {
		Identity.setId(900L);
		Branch branch1 = new Branch(3);
		Branch branch2 = new Branch(3);
		branch2.addToParent(branch1);
		Leaf leaf1 = new Leaf(3);
		leaf1.addToParent(branch2);
		Branch root1 = branch2.getRoot();
		assertEquals(root1, branch1);
		Branch root2 = leaf1.getRoot();
		assertEquals(root2, root1);
	}
	
	
	@Test
	public void addToParentTest() {
		Identity.setId(100L);
		Branch branch1 = new Branch(4);
		Branch branch2 = new Branch(3);
		branch2.addToParent(branch1);
		assertEquals(branch1, branch2.getParent());
	}
	
	@Test
	public void addChildTest() {
		Identity.setId(100L);
		Branch branch1 = new Branch(4);
		Branch branch2 = new Branch(3);
		branch2.addToParent(branch1);
		Leaf leaf1 = new Leaf(4);
		leaf1.addToParent(branch1);
		
	}
	
	
	@Test
	public void setSecurityLevelTest() {
		Identity.setId(100L);
		Branch branch1 = new Branch(4);
		assertTrue(4 == branch1.getSecurityLevel());
		branch1.setSecurityLevel(5);
		assertFalse(4 == branch1.getSecurityLevel());
		assertTrue(5 == branch1.getSecurityLevel());
	}
	
	@Test
	public void containsTest() {
		Branch branch1 = new Branch (3);
		Branch branch2 = new Branch (3);
		branch2.addToParent(branch1);
		Branch branch3 = new Branch (3);
		branch3.addToParent(branch1);
		Branch branch4 = new Branch (3);
		branch4.addToParent(branch3);
		Branch branch5 = new Branch (3);
		branch5.addToParent(branch3);
		
		Leaf leaf2 = new Leaf (3);
		leaf2.addToParent(branch1);
		Leaf leaf3 = new Leaf (3);
		leaf3.addToParent(branch1);
		Leaf leaf4 = new Leaf (3);
		leaf4.addToParent(branch3);
		Leaf leaf5 = new Leaf (3);
		leaf5.addToParent(branch3);

		assertTrue( branch1.contains(branch2));
		assertTrue( branch1.contains(branch3));
		assertTrue( branch1.contains(branch4));
		assertTrue( branch1.contains(branch5));
		
		assertTrue( branch1.contains(leaf2));
		assertTrue( branch1.contains(leaf3));
		assertTrue( branch1.contains(leaf4));
		assertTrue( branch1.contains(leaf5));
		
		assertFalse( branch2.contains(branch3));
		assertFalse( branch2.contains(leaf3));
		assertFalse( branch2.contains(leaf4));
		assertFalse( branch2.contains(leaf5));

	}

	
	
	@Test
	public void checkSecurityLevelTest() {
		Identity.setId(100L);
		Branch branch1 = new Branch(4);
		Branch branch2 = new Branch(3);
		branch2.addToParent(branch1);
	    Branch branch3 = new Branch(2);
		branch3.addToParent(branch2);
	    assertTrue (branch3.checkSecurityLevel(4));
	    assertFalse(branch3.checkSecurityLevel(3));
	    assertFalse(branch3.checkSecurityLevel(2));
	    assertTrue(branch2.checkSecurityLevel(4));
	    assertFalse(branch2.checkSecurityLevel(3));
	    assertTrue(branch1.checkSecurityLevel(5));
	    assertTrue(branch1.checkSecurityLevel(4));
	    assertFalse(branch1.checkSecurityLevel(3));
	}
	
	
	@Test
	public void equalsTest(){
		assertTrue(theBranch.equals(theBranch));
		assertFalse(theBranch.equals(null));
		Object o1 = "OBJECT";
		assertFalse(theBranch.equals(o1));
		Identity.setId(200L);
		Branch branch1 = new Branch(4);
		assertFalse(theBranch.equals(branch1));
	}
	
	
	@Test
	public void hashCodeTest(){
		Identity.setId(200L);
		Branch branch1 = new Branch(4);
		assertFalse( theBranch.hashCode() == branch1.hashCode());
		assertTrue( theBranch.hashCode() != 0);
		assertTrue( branch1.hashCode() != 0);
		assertFalse( branch1.hashCode() == theBranch.hashCode());
	}
	
	
	@Test
	public void toStringTest() {
		Identity.setId(200L);
		Branch branch1 = new Branch(4);
		branch1.addToParent(theBranch);
		assertEquals("Branch{guard{ id[201] level[4]} parent[1] children #=0}", branch1.toString());
	}
	
	
	@Test
	public void compareToTest() {
		Identity.setId(100L);
		Branch branch1 = new Branch(4);
		branch1.addToParent(theBranch);
		Branch branch2  = new Branch(4);
		branch2.addToParent(theBranch);
		assertTrue ( branch1.compareTo(branch2) <  0);
		assertFalse( branch1.compareTo(branch2) == 0);
		assertFalse( branch1.compareTo(branch2) >  0);
		
	} 
	
	
	@Test
	public void acceptsTest() {
        // Tested in UserTest
	}

}
