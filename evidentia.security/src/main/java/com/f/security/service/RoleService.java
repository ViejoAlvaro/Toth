package com.f.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f.security.dto.RoleDTO;
import com.f.security.model.Role;
import com.f.security.model.RoleRegistry;
import com.f.security.model.xml.RoleFromXML;
import com.f.security.repository.RoleRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public RoleDTO createRole(Role role) {
    	RoleDTO roleDTO  = new RoleDTO(role);
        RoleDTO savedDTO = roleRepository.save(roleDTO);
        RoleRegistry.getInstance().addRole(role);
        return savedDTO;
    }


    public Role getRoleByName(String name) {
    	Role role = RoleRegistry.getInstance().getRole(name);
    	if ( role != null) {
    		return role;
    	}
        RoleDTO roleDTO = roleRepository.findByName(name)
                               .orElseThrow(() -> new RuntimeException("Role["+ name+ "] not found"));
    	
        Role.ImporterDirector roleImporter = new RoleFromXML(roleDTO.getRoleXML());
        role = new Role(roleImporter);
        RoleRegistry.getInstance().addRole(role);
        return role;
    }

}
