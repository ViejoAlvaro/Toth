package com.f.metadata.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.f.basic.model.Parm;
import com.f.metadata.model.EnumField;
import com.f.metadata.model.EnumRegistry;
import com.f.metadata.model.EnumType;
import com.f.metadata.model.Field;
import com.f.metadata.model.Schema;

@SpringBootTest
public class SchemaServiceTest {


	@Autowired
	private SchemaService schemaService;

	private static Schema      theSchema;
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
		theSchema = new Schema("THE_SCHEMA", fields);
	}


	@BeforeAll
	static public void setUpBeforeClass() throws Exception {
		System.out.println("))) Testing SchemaService.");
	}



	@Test
	public void saveSchemaTest() {
		Long id = theSchema.getId();
		Schema schema1 = schemaService.saveSchema(theSchema);
		assertEquals(schema1, theSchema);
		Schema schema2 = schemaService.getSchemaById(id);
		assertEquals(schema1, schema2);

	}


	@Test  
	public void getSchemaByIdTest() {
		Long id = theSchema.getId();
		Schema schema3 = schemaService.saveSchema(theSchema);
		assertEquals(schema3, theSchema);
		Schema schema4 = schemaService.getSchemaById(id);
		assertEquals(schema3, schema4);
		Exception exception = assertThrows(RuntimeException.class, () -> {
			schemaService.getSchemaById(5001L);
		});
		String expectedMessage = "Schema[5001] not found";
		String actualMessage = exception.getMessage();
		System.out.println(actualMessage);
		assertTrue(actualMessage.contains(expectedMessage));


	}


	@Test
	public void getSchemaByNameTest() {
		String name = theSchema.getName();
		Schema schema5 = schemaService.saveSchema(theSchema);
		assertEquals(schema5, theSchema);
		Schema schema6 = schemaService.getSchemaByName(name);
		assertEquals(schema5, schema6);
		Exception exception = assertThrows(RuntimeException.class, () -> {
			schemaService.getSchemaByName("***No_Name***");
		});
		String expectedMessage = "Schema[***No_Name***] not found";
		String actualMessage = exception.getMessage();
		System.out.println(actualMessage);
		assertTrue(actualMessage.contains(expectedMessage));

	}


}
