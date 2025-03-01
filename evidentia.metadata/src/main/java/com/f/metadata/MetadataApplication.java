package com.f.metadata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @EnableJpaRepositories(basePackages="com.f.metadata.repository")
// @EntityScan(basePackages = {"com.f.metadata.dto", "com.f.metadata.model"})
@SpringBootApplication
public class MetadataApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(MetadataApplication.class, args);
	}

}
