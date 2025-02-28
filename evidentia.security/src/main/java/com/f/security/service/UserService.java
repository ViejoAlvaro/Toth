package com.f.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f.security.dto.UserDTO;
import com.f.security.model.User;
import com.f.security.model.xml.UserFromXML;
import com.f.security.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDTO createUser(User user) {
    	UserDTO userDTO  = new UserDTO(user);
        UserDTO savedDTO = userRepository.save(userDTO);
 //       UserRegistry.getInstance().addUser(user);
        return savedDTO;
    }


    public User getUserByEmail(String email) {
    /*	User user = UserRegistry.getInstance().getUser(name);
    	if ( user != null) {
    		return user;
    	}
    */
        UserDTO userDTO = userRepository.findByEmail(email)
                               .orElseThrow(() -> new RuntimeException("User["+ email+ "] not found"));
    	
        User.ImporterDirector userImporter = new UserFromXML(userDTO.getUserXML());
        User user = new User(userImporter);
 //       UserRegistry.getInstance().addUser(user);
        return user;
    }


}
