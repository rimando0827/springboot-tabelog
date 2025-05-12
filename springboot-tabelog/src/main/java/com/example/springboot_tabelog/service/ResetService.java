package com.example.springboot_tabelog.service;

import org.springframework.stereotype.Service;

import com.example.springboot_tabelog.entity.User;
import com.example.springboot_tabelog.repository.UserRepository;

@Service
public class ResetService {
	
	private final UserRepository userRepository;
	
	public ResetService(UserRepository userRepository) {
        this.userRepository = userRepository;
        
    }    
	
	
	
	public boolean isSameEmail(String email) {
		User user = userRepository.findByEmail(email);
		
		return user !=  null;
	} 
	
	public User findUserByEmail(String email) {
	    return userRepository.findByEmail(email);
	}

}
