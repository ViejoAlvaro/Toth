package com.f.security.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.f.metadata.model.Schema;

@FeignClient(name = "schema-service", url = "http://localhost:8081")
public interface SchemaClient {

    @GetMapping("/api/schemas/{id}")
    Schema getSchemaById(@PathVariable Long id);

    @GetMapping("/api/schemas/{name}")
    Schema getSchemaByName(@PathVariable String name);

}
