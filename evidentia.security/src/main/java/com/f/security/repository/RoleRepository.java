package com.f.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.f.security.dto.RoleDTO;


public interface RoleRepository extends JpaRepository<RoleDTO, Long>{
	
	public Optional<RoleDTO> findById  (Long ID);
	public Optional<RoleDTO> findByName(String name);

}
