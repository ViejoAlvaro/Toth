package metadata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import objects.Parm;

public class VarTest {
	
	private static Var theVar;
	private static Field theField;
	static {
		theField = new Field( "THE_FIELD", Parm.TYPE.INTEGER);
		theVar = new Var(theField, "12345");
	}
	
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("))) Testing Var");
	}

	@Test 
	public void VarTest1() {
		Var var2 = new Var( theField, "0");
		assertEquals(theVar, theVar);
		assertEquals(var2, var2);
		assertNotEquals(var2, theVar);
	}

	@Test
	public void getFieldTest() {
		Var var3 = new Var( theField, "987");
		Field field1 = new Field("FIELD1", Parm.TYPE.INTEGER);
		assertTrue ( var3.getField().equals(theField));
		assertFalse( var3.getField().equals(field1));
		Field field2 = new Field("THE_FIELD", Parm.TYPE.INTEGER);
		assertEquals( theVar.getField(), field2);
	}

	@Test
	public void setValueTest() {
		theVar.setValue("1000");
		assertEquals(theVar.getValue(), "1000");
		assertNotEquals(theVar.getValue(), "987");

	}

	@Test
	public void getValueTest() {
		Var var3 = new Var( theField, "999");
		assertEquals(var3.getValue(), "999");
		var3.setValue("1000");
		assertEquals(var3.getValue(), "1000");
		assertNotEquals(var3.getValue(), "999");

	}

	@Test
	public void equalsTest(){
		Var var4 = new Var( theField, "135");
		Var var5 = new Var( theField, "7911");
		assertEquals(var4, var4);
		assertEquals(var5, var5);
		assertNotEquals(var4, var5);
		assertNotEquals(var5, null);

	}

	@Test
	public void hashCodeTest() {
		Var var6 = new Var( theField, "654");
		Var var7 = new Var( theField, "321");
		assertTrue(var6.hashCode() != 0);
		assertTrue(var7.hashCode() != 0);
		assertTrue(var6.hashCode() != var7.hashCode());
	}
	
	@Test
	public void toStringTest() {
		Var var8 = new Var( theField, "5000");
		assertEquals(var8.toString(), "Var {Field{ name[the_field] type[INTEGER]}=5000}");
	}

	@Test 
	public void compareToTest() {
		Var var8 = new Var( theField, "654");
		Var var9 = new Var( theField, "321");
		
		assertTrue( theVar.compareTo(var8) < 0);
		assertTrue( var8.compareTo(var9)   > 0);
	}

}
