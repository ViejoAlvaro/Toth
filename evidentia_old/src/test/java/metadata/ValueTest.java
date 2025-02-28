package metadata;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ValueTest {
	
	
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("))) Testing Value");
	}
	

    @Test
	public void getStringValueTest() {
    	Value value1 = new Value("123");
    	assertTrue( value1.getStringValue().equals("123"));
    	Value value2 = new Value("abc");
    	assertFalse( value2.getStringValue().equals("123"));

	}
	
    @Test	
	public void equalsTest(){
    	Value value2 = new Value("XYZ");
    	Value value3 = value2;
    	assertTrue( value2.equals(value2));
    	assertTrue( value2.equals(value3));
    	Value value4 = new Value("XYZ");
    	assertTrue( value2.equals(value4));
    	Value value5 = new Value("234");
    	assertFalse( value5.equals(value2));
    	assertFalse( value5.equals(value3));
    	assertFalse( value5.equals(value4));

	}
    
    
    @Test
	public void hashCodeTest() {
    	Value value2 = new Value("XYZ");
    	assertTrue( value2.hashCode() != 0);
    	Value value4 = new Value("123");
    	assertTrue(  value2.hashCode() == value2.hashCode());
    	assertFalse( value2.hashCode() == value4.hashCode());

	} 
	

    @Test
	public void toStringTest() { 	
    	Value  value5 = new Value("MI VALUE5");
    	assertTrue(value5.toString().equals("[MI VALUE5]"));

	} 
	

    @Test
	public void compareToTest() {
        Value v1 = new Value("Abc");
        Value v2 = new Value("abc");
        Value v3 = new Value("123");
        Value v4 = new Value("$123");
        Value v5 = new Value(" ABC");
        Value v6 = new Value(" abc");
        Value v7 = new Value(" 123");
        Value v8 = new Value(" 123 ");
        
        assertTrue( v1.compareTo(v1) == 0);
        assertTrue( v1.compareTo(v2) < 0);
        assertTrue( v2.compareTo(v1) > 0);
        assertTrue( v1.compareTo(v3) > 0);
        assertTrue( v2.compareTo(v3) > 0);
        assertTrue( v3.compareTo(v3) == 0);
        assertTrue( v1.compareTo(v5) > 0);
        assertTrue( v4.compareTo(v1) < 0);
        assertTrue( v5.compareTo(v2) < 0);
        assertTrue( v5.compareTo(v3) > 0);
        assertTrue( v6.compareTo(v5) > 0);
        assertTrue( v7.compareTo(v6) < 0);
        assertTrue( v7.compareTo(v8) == 0);      
    
	} 
	
}
