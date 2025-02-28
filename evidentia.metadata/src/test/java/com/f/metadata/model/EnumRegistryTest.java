package com.f.metadata.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class EnumRegistryTest {


   private static EnumRegistry theRegistry;
   static {
      theRegistry = EnumRegistry.getInstance();


   }
   
   @BeforeAll
   static void setUpBeforeClass() throws Exception {
      System.out.println("))) Testing EnumRegistry");
      theRegistry.clear();
   }

   
   @Test 
    public void EnumRegistryTest1() {
      EnumRegistry registry1 = EnumRegistry.getInstance();
      EnumRegistry registry2 = EnumRegistry.getInstance();
      assertEquals( theRegistry, registry1);
      assertEquals( theRegistry, registry2);
   }

   @Test
    public void addTypeTest() {
      Set<String> range1 = new TreeSet<String>();
      range1.add("A");
      range1.add("B");
      range1.add("C");
      range1.add("d");
      range1.add("e");
      range1.add("f");
      range1.add("1");
      range1.add("2");
      range1.add("3");

      EnumType  theType;
      theType = new EnumType ( "ENUMTYPE1", range1);
      theRegistry.addType(theType);
      assertEquals( theType, theRegistry.getType("ENUMTYPE1"));

      Set<String> range2 = new TreeSet<String>();
      range2.add("X");
      range2.add("Y");
      range2.add("Z");
      range2.add("s");
      range2.add("t");
      range2.add("w");
      range2.add("4");
      range2.add("5");
      range2.add("6"); 
      EnumType  theType2;
      theType2  = new EnumType ( "ENUMTYPE2", range2);
      EnumRegistry.getInstance().addType(theType2);
      theRegistry.addType(theType);
   }

   @Test
    public void getTypeTest() {
      Set<String> range3 = new TreeSet<String>();
      range3.add("x");
      range3.add("y");
      range3.add("z");
      range3.add("i");
      range3.add("j");
      range3.add("k");
      range3.add("9");
      range3.add("8");
      range3.add("7");
      EnumType type3 = new EnumType ( "ENUMTYPE3", range3);
      theRegistry.addType(type3);
      assertEquals(type3, theRegistry.getType("ENUMTYPE3"));
   }

   @Test
    public void equalsTest() {
      assertEquals(theRegistry, theRegistry);
      EnumRegistry registry1 = EnumRegistry.getInstance();
      EnumRegistry registry2 = EnumRegistry.getInstance();
      assertEquals( theRegistry, registry1);
      assertEquals( theRegistry, registry2);

   }

   @Test
    public void hashCodeTest() {
      assertFalse( theRegistry.hashCode() == 0);
      EnumRegistry registry2 = EnumRegistry.getInstance();
      assertTrue( theRegistry.hashCode() == registry2.hashCode());
   }

   @Test
    public void toStringTest() {
      assertEquals(theRegistry.toString(), "EnumRegistry{ENUMTYPE1 ENUMTYPE2 }");

   }


}
