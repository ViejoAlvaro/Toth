package metadata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import metadata.xml.SchemaFromXML;
import metadata.xml.SchemaToXML;
import objects.Identity;
import objects.Parm;

public class SchemaTest {


	private static Schema       theSchema;
	private static Set<Field>  fields;
	private static Set<String>  range = new TreeSet<String>();
	private static EnumRegistry theRegistry;

	static {
		theRegistry = EnumRegistry.getInstance();
		range.add("A");
		range.add("B");
		range.add("C");
		range.add("D");
		range.add("E");
		range.add("1");
		range.add("2");
		range.add("3");
		EnumType type1 = new EnumType ( "ENUM_NAME1", range);
		theRegistry.addType(type1);

		fields = new LinkedHashSet<Field>();
		fields.add(new Field("field1", Parm.TYPE.STRING));
		fields.add(new Field("field2", Parm.TYPE.INTEGER));
		fields.add(new Field("field3", Parm.TYPE.BOOLEAN));
		fields.add(new Field("field4", Parm.TYPE.DATE));
		fields.add(new EnumField("field5","ENUM_NAME1"));
		theSchema = new Schema("THE_SCHEMA", Parm.ADMIN_SECURITY_LEVEL, fields);
	}

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("))) Testing Schema");
	}



	@Test 
	public void SchemaTest1() {
		Schema schema1 = new Schema("SCHEMA1", Parm.ADMIN_SECURITY_LEVEL, fields);
		assertFalse ( schema1.equals(theSchema));
		assertEquals( schema1, schema1);
	}

	@Test
	public void getNameTest() {
		Schema schema2 = new Schema("SCHEMA2", Parm.ADMIN_SECURITY_LEVEL, fields);
		assertNotEquals(theSchema.getName(), schema2.getName());
		assertEquals(theSchema.getName(), "THE_SCHEMA");
		assertEquals(schema2.getName(), "SCHEMA2");
	}

	@Test
	public void addFieldTest() {
		theSchema.addField(new Field("FIELD1", Parm.TYPE.STRING));
		theSchema.addField(new Field("FIELD2", Parm.TYPE.LIST));
		theSchema.addField(new Field("FIELD3", Parm.TYPE.INTEGER));
		theSchema.addField(new Field("FIELD4", Parm.TYPE.DATE));
		Iterator<Field> fieldIter = theSchema.fieldIterator();
		int i = 0;
		while( fieldIter.hasNext()) {
			Field field = fieldIter.next();
			if (i == 0) {
				assertEquals(field.getName(), "field1");
			}else if( i == 1) {
				assertEquals(field.getName(), "field2");			
			}else if( i == 2) {
				assertEquals(field.getName(), "field3");			
			}else if( i == 3) {
				assertEquals(field.getName(), "field4");			
			}
			i++;
		}
	}

	@Test
	public void sizeTest() {
		Field field10 = new Field("FIELD10", Parm.TYPE.STRING);
		Field field11 = new Field("FIELD11", Parm.TYPE.BOOLEAN);
		Field field12 = new Field("FIELD12", Parm.TYPE.DATE);
		Field field13 = new Field("FIELD13", Parm.TYPE.LIST);
		Field field14 = new Field("FIELD14", Parm.TYPE.INTEGER);
		Set<Field> someFields = new LinkedHashSet<Field>();
		someFields.add(field10);
		someFields.add(field11);
		someFields.add(field12);
		someFields.add(field13);
		someFields.add(field14);		
		Schema schema3 = new Schema("SCHEMA3", Parm.ADMIN_SECURITY_LEVEL, someFields);

		assertTrue(schema3.size() == 5);

	}

	@Test
	public void fieldIteratorTest(){
		Field field20 = new Field("FIELD20", Parm.TYPE.STRING);
		Field field21 = new Field("FIELD21", Parm.TYPE.BOOLEAN);
		Field field22 = new Field("FIELD22", Parm.TYPE.DATE);
		Field field23 = new Field("FIELD23", Parm.TYPE.LIST);
		Field field24 = new Field("FIELD24", Parm.TYPE.INTEGER);
		Set<Field> fields30 = new LinkedHashSet<Field>();
		fields30.add(field20);
		fields30.add(field21);
		fields30.add(field22);
		fields30.add(field23);
		fields30.add(field24);		
		Schema schema4 = new Schema("SCHEMA4", Parm.ADMIN_SECURITY_LEVEL, fields30);
		Iterator<Field> fieldIter = schema4.fieldIterator();
		int i = 0;
		while( fieldIter.hasNext()) {
			Field field = fieldIter.next();
			if (i == 0) {
				assertEquals(field.getName(), "field20");
			}else if( i == 1) {
				assertEquals(field.getName(), "field21");			
			}else if( i == 2) {
				assertEquals(field.getName(), "field22");			
			}else if( i == 3) {
				assertEquals(field.getName(), "field23");			
			}else if( i == 4) {
				assertEquals(field.getName(), "field24");			
			}
			i++;
		}

	}
	
	
	@Test
	public void exportTest() {
		Identity.setId(3000L);
        Set<Field> thisFields;
        thisFields = new LinkedHashSet<Field>();
        thisFields.add(new Field("field1", Parm.TYPE.STRING));
        thisFields.add(new Field("field2", Parm.TYPE.INTEGER));
        thisFields.add(new Field("field3", Parm.TYPE.BOOLEAN));
        thisFields.add(new Field("field4", Parm.TYPE.DATE));
        thisFields.add(new EnumField("field5","ENUM_NAME1"));
        
		Schema schema2 = new Schema("THE_SCHEMA2", Parm.ADMIN_SECURITY_LEVEL, thisFields);
		Schema.Exporter exporter = new SchemaToXML();	
		String actual = (String)schema2.export(exporter);
		String expected = "<Schema name=\"THE_SCHEMA2\">\n"
				+ "   <Toth id=\"3001\" level=\"3\"/>\n"
				+ "   <fields>\n"
				+ "      <field name=\"field1\" type=\"STRING\"/>\n"
				+ "      <field name=\"field2\" type=\"INTEGER\"/>\n"
				+ "      <field name=\"field3\" type=\"BOOLEAN\"/>\n"
				+ "      <field name=\"field4\" type=\"DATE\"/>\n"
				+ "      <field name=\"field5\" type=\"LIST\" enum=\"ENUM_NAME1\"/>\n"
				+ "   </fields>\n"
				+ "</Schema>\n";
			
		assertEquals(expected,actual);
	}
	
	
	@Test
	public void importTest() {
		Identity.setId(3000L);
        Set<Field> thisFields;
        thisFields = new LinkedHashSet<Field>();
        thisFields.add(new Field("field1", Parm.TYPE.STRING));
        thisFields.add(new Field("field2", Parm.TYPE.INTEGER));
        thisFields.add(new Field("field3", Parm.TYPE.BOOLEAN));
        thisFields.add(new Field("field4", Parm.TYPE.DATE));
        thisFields.add(new EnumField("field5","ENUM_NAME1"));
        
		Schema schema2 = new Schema("THE_SCHEMA2", Parm.ADMIN_SECURITY_LEVEL, thisFields);
		Schema.Exporter exporter = new SchemaToXML();	
		String actual = (String)schema2.export(exporter);
	    SchemaFromXML schemaImporter  = new SchemaFromXML( actual ); 
		Schema schema3 = new Schema(schemaImporter);
		String expected = (String)schema3.export(exporter);
		assertEquals(actual, expected);
		assertEquals(schema2, schema3);		
	
	}
	

	@Test
	public void equalsTest(){
		Field field30 = new Field("FIELD30", Parm.TYPE.STRING);
		Field field31 = new Field("FIELD31", Parm.TYPE.BOOLEAN);
		Field field32 = new Field("FIELD32", Parm.TYPE.DATE);
		Field field33 = new Field("FIELD33", Parm.TYPE.LIST);
		Field field34 = new Field("FIELD34", Parm.TYPE.INTEGER);
		Set<Field> fields40 = new LinkedHashSet<Field>();
		fields40.add(field30);
		fields40.add(field31);
		fields40.add(field32);
		fields40.add(field33);
		fields40.add(field34);		
		Schema schema4 = new Schema("SCHEMA5", Parm.ADMIN_SECURITY_LEVEL, fields40);

		assertEquals( theSchema, theSchema);
		assertEquals( schema4, schema4);
		assertNotEquals( schema4, theSchema);
		assertNotEquals( schema4, null);

	}

	@Test
	public void hashCodeTest() {
		Field field30 = new Field("FIELD30", Parm.TYPE.STRING);
		Field field31 = new Field("FIELD31", Parm.TYPE.BOOLEAN);
		Field field32 = new Field("FIELD32", Parm.TYPE.DATE);
		Field field33 = new Field("FIELD33", Parm.TYPE.LIST);
		Field field34 = new Field("FIELD34", Parm.TYPE.INTEGER);
		Set<Field> fields40 = new LinkedHashSet<Field>();
		fields40.add(field30);
		fields40.add(field31);
		fields40.add(field32);
		fields40.add(field33);
		fields40.add(field34);		
		Schema schema4 = new Schema("SCHEMA6", Parm.ADMIN_SECURITY_LEVEL, fields40);

		assertFalse( theSchema.hashCode() == 0);
		assertTrue ( theSchema.hashCode() != schema4.hashCode());

	}

	@Test 
	public void toStringTest() {
		assertEquals( theSchema.toString(), "Schema{THE_SCHEMA Fields[field1 field2 field3 field4 field5 ]}");
	}

	@Test 
	public void compareToTest() {
		Field field30 = new Field("FIELD30", Parm.TYPE.STRING);
		Field field31 = new Field("FIELD31", Parm.TYPE.BOOLEAN);
		Field field32 = new Field("FIELD32", Parm.TYPE.DATE);
		Field field33 = new Field("FIELD33", Parm.TYPE.LIST);
		Field field34 = new Field("FIELD34", Parm.TYPE.INTEGER);
		Set<Field> fields40 = new LinkedHashSet<Field>();
		fields40.add(field30);
		fields40.add(field31);
		fields40.add(field32);
		fields40.add(field33);
		fields40.add(field34);		
		Schema schema4 = new Schema("SCHEMA6", Parm.ADMIN_SECURITY_LEVEL, fields40);

		assertTrue( schema4.compareTo(schema4) == 0);
		assertTrue( schema4.compareTo(theSchema) < 0);
		assertTrue( theSchema.compareTo(schema4) > 0);

	}


}
