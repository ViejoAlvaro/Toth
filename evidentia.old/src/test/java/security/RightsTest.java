package security;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import objects.Branch;
import objects.Classification;
import objects.Identity;
import objects.Leaf;
import objects.Parm;

public class RightsTest {
   
   private static Rights         theRights;
   private static Rights         theRights2;
   private static DataObject     theObject;
   private static Branch         theBranch;
   private static Leaf           theLeaf;
   static {
      Identity.setId(0L);
      Classification classification1   = new Classification (Parm.ADMIN_SECURITY_LEVEL, "CLASSIFICATION_1");
      Classification classification2   = new Classification (Parm.ADMIN_SECURITY_LEVEL, "CLASSIFICATION_2");
      Branch branch1 = new Branch (3);
      classification1.addBranch(branch1, classification1.getRoot());
      Branch branch2 = new Branch (3);
      classification1.addBranch(branch2, branch1);
      Branch branch3 = new Branch (3);
      classification1.addBranch(branch3, branch1);
      Branch branch4 = new Branch (3);
      classification1.addBranch(branch4, branch3);
      Branch branch5 = new Branch (3);
      classification1.addBranch(branch5, branch3);
      theBranch = new Branch(3);
      classification2.addBranch(theBranch, classification2.getRoot());     
      
      theLeaf = new Leaf(5);
      classification1.addLeaf(theLeaf, branch5);
      Leaf leaf2 = new Leaf (3);
      classification1.addLeaf(leaf2, branch1);
      Leaf leaf3 = new Leaf (3);
      classification1.addLeaf(leaf3, branch1);
      Leaf leaf4 = new Leaf (3);
      classification1.addLeaf(leaf4, branch3);
      Leaf leaf5 = new Leaf (0);
      classification1.addLeaf(leaf5, branch3);

      Identity.setId(0L);
      theRights  = new Rights(  Parm.ADMIN_SECURITY_LEVEL, classification1);
      theRights2 = new Rights(  Parm.ADMIN_SECURITY_LEVEL, classification2);
      theObject  = new DataObject(Parm.ADMIN_SECURITY_LEVEL);
      Leaf leaf6 = new Leaf( 5);
      classification1.addLeaf(leaf6, branch5);
      theRights.grant(leaf6);
   }
   
   @BeforeAll
   public static void beforeAll() {
      System.out.println("))) Testing Rights");
      Identity.setId(0L);
   }

   
   @Test
   public void grantTest( ) {
      Identity.setId(500L);
      assertFalse( theRights.isGrantedAccess(theObject));
      DataObject object1 = new DataObject(1);
      DataObject object2 = new DataObject(1);
      Exception exception = assertThrows(IllegalArgumentException.class, () -> {
         theRights.grant(object1);
          });
      String expectedMessage = "The classification schema does not contain the object[501]";
      String actualMessage = exception.getMessage();
       assertTrue(actualMessage.contains(expectedMessage));

      assertFalse( theRights.isGrantedAccess(object1));
      assertFalse( theRights.isGrantedAccess(object2));
      theRights.revoke(theLeaf);
      assertFalse(theRights.isGrantedAccess(theLeaf));
      theRights.grant(theLeaf);
      assertTrue(theRights.isGrantedAccess(theLeaf));
   }
   
   @Test
   public void revokeTest() {
      Identity.setId(600L);
      theRights.revoke(theLeaf);
      assertFalse(theRights.isGrantedAccess(theLeaf));
      theRights.grant(theLeaf);
      assertTrue(theRights.isGrantedAccess(theLeaf));

      DataObject object2 = new DataObject(3);
      try {
      theRights.grant(object2);
      assertTrue( theRights.isGrantedAccess(object2));   
      }catch (Exception e)
      {
           assertFalse( theRights.isGrantedAccess(object2));
      }
      theRights.revoke(object2);
      assertFalse( theRights.isGrantedAccess(object2));  
       
   }

   
   @Test
   public void isGrantedAccessTest( ) {
      Identity.setId(700L);
      theRights.grant(theLeaf);
      assertTrue( theRights.isGrantedAccess(theLeaf));
      DataObject otherObject = new DataObject(4);
      assertFalse( theRights.isGrantedAccess(otherObject));
      theRights.revoke(theLeaf);
   }
   
   @Test
   public void getRightsNumberTest() {
      Identity.setId(400L);
      assertTrue(theRights2.getRightsNumber() == 0);
      assertTrue(theRights.getRightsNumber() > 0);
      theRights2.grant(theBranch);
      assertTrue(theRights2.getRightsNumber() == 1);
   }
   

   @Test
   public void equalsTest() {
      assertTrue(theRights.equals(theRights));
      assertFalse(theRights.equals(theRights2));
      Object obj = "THERIGHTS";
      assertFalse(theRights.equals(obj));
      Rights otherRights = theRights;
      assertTrue(theRights.equals(otherRights));
      
   }
   
   
   @Test
   public void hashCodeTest() {
      assertFalse(theRights.hashCode() == 0);
      assertTrue(theRights.hashCode() != theRights2.hashCode());
      Rights other = theRights2;
      assertTrue(theRights2.hashCode() == other.hashCode());
   }
   
   
   @Test
   public void toStringTest() {
      assertTrue(theRights.toString().equals("Rights{ schema[CLASSIFICATION_1] rights#=1}")); 
   }
   
}
