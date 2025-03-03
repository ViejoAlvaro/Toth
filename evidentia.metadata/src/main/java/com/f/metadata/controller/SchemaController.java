package com.f.metadata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.f.metadata.model.Schema;
import com.f.metadata.service.SchemaService;

@RestController
@RequestMapping("/schemas")
public class SchemaController {

	@Autowired
    private SchemaService schemaService;

/*    @Autowired
    public SchemaController(SchemaService schemaService) {
        this.schemaService = schemaService;
    }
*/

    @GetMapping("/{id}")
    public ResponseEntity<Schema> getSchemaById(@PathVariable Long id) {
        Schema schema = schemaService.getSchemaById(id);
        return ResponseEntity.ok(schema);
    }

    @GetMapping("/getSchema")    
    public ResponseEntity<Schema> getSchemaByName(@RequestParam  String name) {
        Schema schema = schemaService.getSchemaByName(name);
        return ResponseEntity.ok(schema);
    }
    
    @RequestMapping(value = "/addSchema", method = RequestMethod.POST)
    public ResponseEntity<Schema> addSchema(@RequestBody Schema schema){
    	return ResponseEntity.ok(schema);
    }

}
