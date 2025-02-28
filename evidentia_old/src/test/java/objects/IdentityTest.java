package objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class IdentityTest {
	
	@BeforeAll
	public static void beforeAll() {
		System.out.println("))) Testing Identity");
	    Identity.setId(0L);
	}

	
	@Test
	public void IdentityTest1(){
		
		@SuppressWarnings("unused")
		Identity theIdentity = new Identity(4000);
		assertEquals(4000L, Identity.getId());
		
	}
	
    @Test
    public void currentTest() {
    	Identity.setId(100L);
        assertEquals(100L, Identity.getId());
    }
	
    @Test
    public void  setIdTest() {
    	Identity.setId(100L);
        assertEquals(101L, Identity.assignId());
    }
}
