package metadata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import objects.Parm;

public class MetadataTest {


	private static Metadata     theMeta;
	private static Schema       theSchema;
	private static Set<String>  range;
	private static EnumType     theType;
	private static EnumRegistry theRegistry;
	private static List<Var>    theVars;
	private static List<String> theValues;
	private static Set<Field>   fields;

	static {
		fields = new LinkedHashSet<Field>();
		Field field1 = new Field("field1", Parm.TYPE.STRING);
		Field field2 = new Field("field2", Parm.TYPE.INTEGER);
		Field field3 = new Field("field3", Parm.TYPE.BOOLEAN);
		Field field4 = new Field("field4", Parm.TYPE.DATE);
		Field field5 = new Field("field5", Parm.TYPE.LIST);
		fields.add(field1);
		fields.add(field2);
		fields.add(field3);
		fields.add(field4);
		fields.add(field5);
		theSchema = new Schema("THE_SCHEMA", Parm.ADMIN_SECURITY_LEVEL, fields);
		
		range = new TreeSet<String>();
		range.add("A");
		range.add("B");
		range.add("C");
		range.add("D");
		range.add("E");
		range.add("1");
		range.add("2");
		range.add("3");
		theType     = new EnumType ( "type1", range);
		theRegistry = EnumRegistry.getInstance();
		theRegistry.addType(theType);

		
		theVars = new ArrayList<Var>();
		theVars.add(new Var(field1, "STRING_VALUE"));
		theVars.add(new Var(field2, "12345"));
		theVars.add(new Var(field3, "TRUE"));
		theVars.add(new Var(field4, "2025-02-03T06:03:01.501476700"));
		theVars.add(new Var(field5,  "A"));
		
		
		List<String> values = new ArrayList<String>();
		values.add("STRING_VALUE");
		values.add("12345");
		values.add("TRUE");
		values.add("2025-02-03T06:03:01.501476700");
		values.add( "A");
		theValues = values;
		
		theMeta = new Metadata("THE_META",theSchema, values);
		
	}
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("))) Testing Metadata");
	}
	

	
	@Test 
	public void MetadataTest1() {
		assertTrue( theMeta.equals(theMeta));
		Metadata meta2 = new Metadata("META2", theSchema, theValues);
		assertNotEquals(theMeta, meta2);
	}
	

	@Test
	public void getNameTest() {
		assertEquals(theMeta.getName(), "THE_META");

	}
	

	@Test
	public void	getSchemaTest() {
		assertEquals(theMeta.getSchema(), theSchema);
	}
	

	@Test
	public void varIteratorTest(){
		Iterator<Var> varIter = theMeta.varIterator();
		int i=0;
		while(varIter.hasNext()) {
			Var v = varIter.next();
			assertTrue(v.equals(theVars.get(i)));
			i++;
		}
	}
	

	@Test
	public void equalsTest(){
		Metadata meta3 = new Metadata("META3", theSchema, theValues);
		assertEquals(meta3, meta3);
		assertNotEquals(theMeta, meta3);
	}
	

	@Test
	public void hashCodeTest() {
		Metadata meta4 = new Metadata("META4", theSchema, theValues);
		assertTrue( meta4.hashCode() != 0);
		assertFalse( meta4.hashCode() == theMeta.hashCode());

	}

	@Test
	public void toStringTest() {
		Metadata meta5 = new Metadata("META4", theSchema, theValues);
		assertEquals(meta5.toString(), "Metadata{ name[META4] schema[THE_SCHEMA] values#=5}");
	}

	@Test 
	public void compareToTest() {
		Metadata meta6 = new Metadata("META6", theSchema, theValues);
		assertTrue( meta6.compareTo(meta6) == 0);
		assertTrue( meta6.compareTo(theMeta) < 0);
		assertTrue( theMeta.compareTo(meta6) > 0);
	}


}
