package metadata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class EnumValueTest {


	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("))) Testing EnumValue");
	}


	@Test
	public void EnumValueTest1() {
		Set<String>  range;
		EnumRegistry theRegistry;
		range = new TreeSet<String>();
		range.add("A");
		range.add("B");
		range.add("C");
		range.add("D");
		range.add("E");
		range.add("1");
		range.add("2");
		range.add("3");

		theRegistry = EnumRegistry.getInstance();

		EnumType    theEnumType;
		theEnumType = new EnumType ( "type_X", range);
		theRegistry.addType(theEnumType);
		EnumValue value1= new EnumValue( "TYPE_X", "A");
		assertEquals(value1.getStringValue(), "A");
		assertFalse( value1.getStringValue().equals("X"));
	}

}
