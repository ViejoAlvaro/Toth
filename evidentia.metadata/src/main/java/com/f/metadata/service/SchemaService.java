package com.f.metadata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.f.metadata.dto.SchemaDTO;
import com.f.metadata.model.Schema;
import com.f.metadata.model.SchemaRegistry;
import com.f.metadata.model.xml.SchemaFromXML;
import com.f.metadata.model.xml.SchemaToXML;
import com.f.metadata.repository.SchemaRepository;

import jakarta.annotation.PostConstruct;

/**
 * Services the schemas stored in the repository
 */
@Service
@Scope("singleton")
public class SchemaService {
	
	@Autowired
    private SchemaRepository schemaRepository;

    @PostConstruct
    public void init() {
     System.out.println("schemaRepository == null?["+ (schemaRepository == null)+ "]");
    }
    
 
    public Schema saveSchema( Schema schema) {
    	Schema.Exporter schemaToXMLexporter = new SchemaToXML();
    	String fieldsXML = (String)schema.export(schemaToXMLexporter);
    	SchemaDTO schemaDTO = new SchemaDTO(schema.getId(), schema.getName(), fieldsXML);
    	schemaRepository.save(schemaDTO);
    	return schema;
    }
    
    
    
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
    
    
    public long count() {
    	return schemaRepository.count();
    }

}
