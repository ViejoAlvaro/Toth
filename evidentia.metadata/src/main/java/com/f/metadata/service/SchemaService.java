package com.f.metadata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f.metadata.dto.SchemaDTO;
import com.f.metadata.model.Schema;
import com.f.metadata.model.SchemaRegistry;
import com.f.metadata.model.xml.SchemaFromXML;
import com.f.metadata.repository.SchemaRepository;

/**
 * Services the schemas stored in the repository
 */
@Service
public class SchemaService {

    @Autowired
    private SchemaRepository schemaRepository;

    public Schema getSchemaById(Long id) {
        SchemaDTO schemaDTO = schemaRepository.findById(id)
                               .orElseThrow(() -> new RuntimeException("Schema["+ id+ "] not found"));
    	
        Schema.ImporterDirector schemaImporter = new SchemaFromXML(schemaDTO.getFieldsXML());
        Schema schema = new Schema(schemaImporter);
        SchemaRegistry.getInstance().addSchema(schema);
        return schema;
    }

    public Schema getSchemaByName(String name) {
    	Schema schema = SchemaRegistry.getInstance().getSchema(name);
    	if ( schema != null) {
    		return schema;
    	}
        SchemaDTO schemaDTO = schemaRepository.findByName(name)
                               .orElseThrow(() -> new RuntimeException("Schema["+ name+ "] not found"));
    	
        Schema.ImporterDirector schemaImporter = new SchemaFromXML(schemaDTO.getFieldsXML());
        schema = new Schema(schemaImporter);
        SchemaRegistry.getInstance().addSchema(schema);
        return schema;
    }

}
