package com.f.security.dto;

import com.f.security.model.Role;
import com.f.security.model.xml.RoleToXML;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class RoleDTO {

	/*
	 * id        Role id
	 * name      name of the role
	 * roleXML   XML version of the role 
	 */
	@Id
	private Long          id;
	private String 		  name;
	private String        roleXML;
	
	public RoleDTO( Role role) {
		if (role == null) {
			throw new NullPointerException("Role of the roleDTO can't be null");
		}
	
		this.id      = role.getId();
		this.name    = role.getName();
		this.roleXML = (String)role.export(new RoleToXML());
		
	}
	
	public Long   getId() { return id;}
	public void   setId(Long id) { this.id = id;}
	
	public String getName() { return name;}
	public void   setName(String name) { this.name = name; }
	
	public String getRoleXML() { return roleXML;}
	public void   setRoleXML(String roleXML) { this.roleXML = roleXML;}

}
