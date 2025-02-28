package com.f.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.f.security.dto.UserDTO;

public interface UserRepository  extends JpaRepository<UserDTO, Long>{
	
	public Optional<UserDTO> findById   (Long ID);
	public Optional<UserDTO> findByEmail(String email);

}
