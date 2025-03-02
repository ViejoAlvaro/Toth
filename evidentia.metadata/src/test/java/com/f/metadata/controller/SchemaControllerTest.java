package com.f.metadata.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.f.metadata.service.SchemaService;

@SpringBootTest
@AutoConfigureMockMvc
class SchemaControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private SchemaService schemaService;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("))) Testing SchemaController");	
	}

    @Test
    public void testGetSchemaById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/schemas")
                        .param("id", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        //        .andExpect(MockMvcResultMatchers.content().string("Hello, John!"));
    }

    @Test
    public void testGetSchemaByName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/schemas")
                .param("name", "Pedro"))
                .andExpect(MockMvcResultMatchers.status().isOk());
     //           .andExpect(MockMvcResultMatchers.content().string("Hello, World!"));
    }

}
