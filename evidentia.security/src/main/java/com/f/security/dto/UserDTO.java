package com.f.security.dto;

import com.f.security.model.User;
import com.f.security.model.xml.UserToXML;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UserDTO {

	/*
	 * id        User id
	 * name      name of the user
	 * userXML   XML version of the user 
	 */
	@Id
	private Long     id;
	private String 	 name;
	private String   userXML;
	
	public UserDTO( User user) {
		if (user == null) {
			throw new NullPointerException("User of the userDTO can't be null");
		}
	
		this.id      = user.getId();
		this.name    = user.getName();
		this.userXML = (String)user.export(new UserToXML());
		
	}
	
	public Long   getId() { return id;}
	public void   setId(Long id) { this.id = id;}
	
	public String getName() { return name;}
	public void   setName(String name) { this.name = name; }
	
	public String getUserXML() { return userXML;}
	public void   setUserXML(String userXML) { this.userXML = userXML;}


}
