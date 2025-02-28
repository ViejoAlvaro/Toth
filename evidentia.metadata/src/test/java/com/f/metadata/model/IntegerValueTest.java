package com.f.metadata.model;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class IntegerValueTest {
   
   
   @BeforeAll
   static void setUpBeforeClass() throws Exception {
      System.out.println("))) Testing IntegerValue");
   }


   @Test
   public void IntegerValueTest1() {
      IntegerValue intVal = new IntegerValue( 2147483647);
      Long longVal = intVal.getIntegerValue();
      assertTrue( longVal.intValue() == 2147483647);
      IntegerValue intVal2 = new IntegerValue( 2147483648L);
      Long longVal2 = intVal2.getIntegerValue();
      assertTrue( longVal2.longValue() == 2147483648L);

   }


   @Test
   public void IntegerValueTest2() {
      IntegerValue intVal = new IntegerValue( 2147483649L);
      Long longVal = intVal.getIntegerValue();
      assertTrue( longVal.longValue() == 2147483649L);
      IntegerValue intVal2 = new IntegerValue( 2147483647);
      Long longVal2 = intVal2.getIntegerValue();
      assertTrue( longVal2.intValue() == 2147483647);
   }


   @Test
   public void IntegerValueTest3 ( ) {
      IntegerValue intVal = new IntegerValue( "2147483649");
      Long longVal = intVal.getIntegerValue();
      assertTrue( longVal.longValue() == 2147483649L);
      IntegerValue intVal2 = new IntegerValue( "2147483647");
      Long longVal2 = intVal2.getIntegerValue();
      assertTrue( longVal2.intValue() == 2147483647);    
      IntegerValue intVal3 = new IntegerValue( "21474836471000");
      Long longVal3 = intVal3.getIntegerValue();
      assertTrue( longVal3.longValue() == 21474836471000L); 
      Exception exception = assertThrows(IllegalArgumentException.class, () -> {
         @SuppressWarnings("unused")
         IntegerValue intVal4 = new IntegerValue(null);
      });
      String expectedMessage = "Value[null] is not an integer";
      String actualMessage = exception.getMessage();
      assertTrue(actualMessage.contains(expectedMessage));
   }



   @Test
   public  void   getIntegerValueTest() { 

      String  strVal = "1000000000000";
      IntegerValue  intVal = new IntegerValue(strVal);
      Long longVal = intVal.getIntegerValue();
      assertTrue(  longVal.toString().equals(strVal));
   }

}
