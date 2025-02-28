package com.f.metadata.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DateValueTest {
	
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("))) Testing DateValue");
	}

	
	@Test
	public void DateValueTest1() {
		LocalDateTime now =  LocalDateTime.now();
		DateValue dateVal = new DateValue(now);
		LocalDateTime timeVal1 = dateVal.getDateTimeValue();
		assertTrue( timeVal1.equals(now));
	}

    @Test
	public void DateValueTest2() {
    	String dateTime = "2025-02-03T06:03:01.501476700";
		DateValue dateVal = new DateValue(dateTime);
		LocalDateTime timeVal1 = dateVal.getDateTimeValue();
		LocalDateTime dateTime1 = LocalDateTime.parse(dateTime);
		assertTrue( timeVal1.equals(dateTime1));

	}

    @Test
	public void getDateTimeValueTest() {
		LocalDateTime now =  LocalDateTime.now();
		DateValue dateVal = new DateValue(now);
		LocalDateTime timeVal1 = dateVal.getDateTimeValue();
		assertTrue( timeVal1.equals(now));
	}		

}
