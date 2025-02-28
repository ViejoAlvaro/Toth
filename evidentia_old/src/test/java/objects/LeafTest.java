package objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LeafTest {

	private static Leaf   theLeaf;
	private static Branch theBranch;
	static {
		Identity.setId(100L);
		theLeaf   = new Leaf  ( Parm.ADMIN_SECURITY_LEVEL);
		theBranch = new Branch( Parm.ADMIN_SECURITY_LEVEL);
	}
	
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("))) Testing Leaf");
		Identity.setId(200L);
	}
	
	@Test
	public void leafTest1() {
		Identity.setId(300L);
		Leaf leaf1 = new Leaf(5);
		assertEquals(301, leaf1.getId());
	}

	@Test
	public void getGuardTest() {
		Identity.setId(400L);
		Leaf aLeaf = new Leaf(3);
		assertEquals(aLeaf.getGuard().toString(), "Leaf{id[401] level[3] parent[root]}");
	}
	
	@Test	
	public void getIdTest() {
		Identity.setId(500L);
		Leaf aLeaf = new Leaf(3);
		assertEquals(501, aLeaf.getId());
	}
	
	
	@Test
	public void getSecurityLevelTest() {
		Identity.setId(600L);
		Leaf aLeaf = new Leaf(3);
		assertEquals(3, aLeaf.getSecurityLevel());
	}
	
	
	@Test
	public void getParentTest(){
		Identity.setId(700L);
		Branch branch1 = new Branch(4);
		Leaf leaf2 = new Leaf(3);
		leaf2.addToParent(branch1);
		assertEquals(branch1, leaf2.getParent());
	}
	
	
	@Test
	public void addToParentTest() {
		Identity.setId(800L);
		Branch branch1 = new Branch(4);
		Leaf leaf2 = new Leaf(3);
		leaf2.addToParent(branch1);
		assertEquals(branch1, leaf2.getParent());
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
	public void setSecurityLevelTest() {
		Identity.setId(1000L);
		Leaf Leaf1 = new Leaf(4);
		assertTrue(4 == Leaf1.getSecurityLevel());
		Leaf1.setSecurityLevel(5);
		assertFalse(4 == Leaf1.getSecurityLevel());
		assertTrue(5 == Leaf1.getSecurityLevel());
	}
	
	
	@Test
	public void checkSecurityLevelTest() {
		Identity.setId(1100L);
		Branch branch1 = new Branch(4);
		Branch branch2 = new Branch(3);
		branch2.addToParent(branch1);
	    Leaf Leaf3 = new Leaf(2);
		Leaf3.addToParent(branch2);
	   assertTrue (Leaf3.checkSecurityLevel(4));
	   assertFalse(Leaf3.checkSecurityLevel(3));
	   assertFalse(Leaf3.checkSecurityLevel(2));
	   assertTrue(branch2.checkSecurityLevel(4));
	   assertFalse(branch2.checkSecurityLevel(3));
	   assertTrue(branch1.checkSecurityLevel(5));
	   assertTrue(branch1.checkSecurityLevel(4));
	   assertFalse(branch1.checkSecurityLevel(3));
	}
	
	
	@Test
	public void equalsTest(){
		Identity.setId(1200L);
		assertTrue(theLeaf.equals(theLeaf));
		assertFalse(theLeaf.equals(null));
		Object o1 = "LEAF";
		assertFalse(theLeaf.equals(o1));
		Identity.setId(200L);
		Leaf leaf1 = new Leaf(4);
		assertFalse(theLeaf.equals(leaf1));
	}
	
	
	@Test
	public void hashCodeTest(){
		Identity.setId(1300L);
		Leaf leaf1 = new Leaf(4);
		assertFalse( theLeaf.hashCode() == leaf1.hashCode());
		assertTrue( theLeaf.hashCode() != 0);
		assertTrue( leaf1.hashCode() != 0);
		assertFalse( leaf1.hashCode() == theLeaf.hashCode());
	}
	
	
	@Test
	public void toStringTest() {
		Identity.setId(1400L);
		Leaf leaf1 = new Leaf(4);
		assertEquals("Leaf{id[1401] level[4] parent[root]}", leaf1.toString());
		leaf1.addToParent(theBranch);
		assertEquals("Leaf{id[1401] level[4] parent[102]}", leaf1.toString());
	}
	
	
	@Test
	public void compareToTest() {
		Identity.setId(1500L);
		Leaf leaf1 = new Leaf(4);
		leaf1.addToParent(theBranch);
		Leaf leaf2  = new Leaf(4);
		leaf2.addToParent(theBranch);
		assertTrue ( leaf1.compareTo(leaf2) <  0);
		assertFalse( leaf1.compareTo(leaf2) == 0);
		assertFalse( leaf1.compareTo(leaf2) >  0);
		
	} 

	
	public void acceptsTest() {
        // Tested in UserTest
	}

}
