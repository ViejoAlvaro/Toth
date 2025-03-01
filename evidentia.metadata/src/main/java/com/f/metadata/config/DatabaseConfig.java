package com.f.metadata.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.f.metadata.repository")
@EntityScan(basePackages = {"com.f.metadata.model", "com.f.metadata.dto"})
public class DatabaseConfig {
}


