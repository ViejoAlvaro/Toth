package com.f.metadata.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.f.metadata.dto.SchemaDTO;

public interface SchemaRepository extends JpaRepository<SchemaDTO, Long> {
	
	public Optional<SchemaDTO> findByName(String name);

}
