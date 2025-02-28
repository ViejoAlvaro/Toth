package objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ClassificationTest {

	private static Classification theClassification;
	
	
	static {
		Identity.setId(0L);
		theClassification  = new Classification(Parm.ADMIN_SECURITY_LEVEL, "THE_CLASSIFICATION");
	}


	@BeforeAll
	public static void beforeAll() {
		System.out.println("))) Testing Classification");
		Identity.setId(0L);
	}

	@Test
	public void getNameTest() {
		Identity.setId(10L);
		assertEquals("THE_CLASSIFICATION", theClassification.getName());
		Classification classification1 = new Classification (Parm.ADMIN_SECURITY_LEVEL, "CLASSIFICATION_1");
		assertEquals("CLASSIFICATION_1", classification1.getName());
	}
	
	@Test
	public void getRootTest() {
		Identity.setId(10L);
		Classification classification1 = new Classification (Parm.ADMIN_SECURITY_LEVEL, "CLASSIFICATION_1");
		Branch root = classification1.getRoot();
		assertEquals("Branch{guard{ id[12] level[3]} parent[nodeIsroot] children #=0}", root.toString());
		
	}
	
	@Test
	public void addBranchTest() {
		Identity.setId(30L);
		Classification classification1 = new Classification (Parm.ADMIN_SECURITY_LEVEL, "CLASSIFICATION_1");
		Classification classification2 = new Classification (Parm.ADMIN_SECURITY_LEVEL, "CLASSIFICATION_2");
		Branch branch1 = new Branch (Parm.ADMIN_SECURITY_LEVEL);
		classification1.addBranch(branch1, classification1.getRoot());
		Branch branch2 = new Branch (Parm.ADMIN_SECURITY_LEVEL);
		classification2.addBranch(branch2, classification2.getRoot());
		assertTrue (classification1.findComponent(branch1));
		assertFalse(classification2.findComponent(branch1));
		assertTrue (classification2.findComponent(branch2));
		assertFalse(classification1.findComponent(branch2));
		
	}
	
	@Test
	public void findBranch() {
		Classification classification1 = new Classification (Parm.ADMIN_SECURITY_LEVEL, "CLASSIFICATION_1");
		Classification classification2 = new Classification (Parm.ADMIN_SECURITY_LEVEL, "CLASSIFICATION_2");
		Branch branch1 = new Branch (3);
		classification1.addBranch( branch1, classification1.getRoot());
		Branch branch2 = new Branch (3);
		classification1.addBranch( branch2, branch1);
		Branch branch3 = new Branch (3);
		classification1.addBranch( branch3, branch1);
		Branch branch4 = new Branch (3);
		classification1.addBranch(branch4, branch3);
		Branch branch5 = new Branch (3);
		classification1.addBranch(branch5, branch3);
		
		Leaf leaf2 = new Leaf (3);
		classification1.addLeaf(leaf2, branch1);
		Leaf leaf3 = new Leaf (3);
		classification1.addLeaf(leaf3, branch1);
		Leaf leaf4 = new Leaf (3);
		classification1.addLeaf(leaf4, branch3);
		Leaf leaf5 = new Leaf (3);
		classification1.addLeaf(leaf5, branch3);

		assertTrue( classification1.findComponent(branch2));
		assertTrue( classification1.findComponent(branch3));
		assertTrue( classification1.findComponent(branch4));
		assertTrue( classification1.findComponent(branch5));
		
		assertTrue( classification1.findComponent(leaf2));
		assertTrue( classification1.findComponent(leaf3));
		assertTrue( classification1.findComponent(leaf4));
		assertTrue( classification1.findComponent(leaf5));
		
		assertFalse( classification2.findComponent(branch2));
		assertFalse( classification2.findComponent(branch3));
		assertFalse( classification2.findComponent(branch4));
		assertFalse( classification2.findComponent(branch5));
		assertFalse( classification2.findComponent(leaf3));
		assertFalse( classification2.findComponent(leaf4));
		assertFalse( classification2.findComponent(leaf5));
		
	}


	@Test
	public void addLeafTest() {
		Identity.setId(40L);
		Classification classification1 = new Classification (Parm.ADMIN_SECURITY_LEVEL, "CLASSIFICATION_1");
		Classification classification2 = new Classification (Parm.ADMIN_SECURITY_LEVEL, "CLASSIFICATION_2");
		Leaf leaf1 = new Leaf (Parm.ADMIN_SECURITY_LEVEL);
		classification1.addLeaf(leaf1, classification1.getRoot());
		Leaf leaf2 = new Leaf (Parm.ADMIN_SECURITY_LEVEL);
		classification2.addLeaf(leaf2, classification2.getRoot());
		assertTrue (classification1.findComponent(leaf1));
		assertFalse(classification2.findComponent(leaf1));
		assertTrue (classification2.findComponent(leaf2));
		assertFalse(classification1.findComponent(leaf2));

	}

	@Test
	public void acceptsTest() {
        // Tested in UserTest
	}

	@Test
	public void equalsTest(){
		Identity.setId(50L);
		Classification classification1 = new Classification (Parm.ADMIN_SECURITY_LEVEL, "CLASSIFICATION_1");
		Classification classification2 = new Classification (Parm.ADMIN_SECURITY_LEVEL, "CLASSIFICATION_1");
		assertEquals(theClassification, theClassification);
		assertFalse (classification1.equals(classification2));
		assertFalse (theClassification.equals(classification1));
	}

	@Test
	public void hashCodeTest() {
		Identity.setId(60L);
		Classification classification1 = new Classification (Parm.ADMIN_SECURITY_LEVEL, "CLASSIFICATION_1");
		Classification classification2 = new Classification (Parm.ADMIN_SECURITY_LEVEL, "CLASSIFICATION_2");
		assertTrue( classification1.hashCode() != 0);
		assertTrue( classification2.hashCode() != 0);
		assertTrue( classification1.hashCode() != classification2.hashCode());
	} 

	@Test
	public void toStringTest() {
		Identity.setId(70L);
		Classification classification1 = new Classification (Parm.ADMIN_SECURITY_LEVEL, "CLASSIFICATION_1");
		assertEquals("Classification{ name[CLASSIFICATION_1] guard{ id[71] level[3]} root[Branch{guard{ id[72] level[3]} parent[nodeIsroot] children #=0}]}",
				     classification1.toString());
	} 

	@Test
	public void compareToTest(){
		Identity.setId(80L);
		Classification classification1 = new Classification (Parm.ADMIN_SECURITY_LEVEL, "CLASSIFICATION_1");
		Classification classification2 = new Classification (Parm.ADMIN_SECURITY_LEVEL, "CLASSIFICATION_2");
		assertTrue( classification1.compareTo(classification1) == 0);
		assertTrue( classification1.compareTo(classification2)  < 0);
		assertTrue( classification2.compareTo(classification1)  > 0);
	} 

}
