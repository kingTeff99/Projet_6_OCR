package com.buddy.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buddy.model.Users;
import com.buddy.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service @Transactional @Slf4j
public class UsersService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws NotFoundException {
		
		Users user = userRepository.findByUsername(username);
		
		if(user == null) {
			
			log.error("User not found in the DB");
			
			throw new NotFoundException("User not found in the DB");
			
		} else {
			
			log.info("User found in the DB : {}", username);
		}
		
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);

	}
	
	/**
	 * Get an user
	 * @param username
	 * @return User
	 */
	public Users getUser(String username) {
		
		if(username == null) {
			
			return null;
			
		}
		
		log.info("Fetching user {}", username);
		
		return userRepository.findByUsername(username);
		
	}
	
	/**
	 * Get an user by id
	 * @param id
	 * @return user or null
	 */
	public Optional<Users> getUserById(Long id) {
		
		if(id == null) {
			
			return Optional.empty();
			
		}
		
		log.info("Users found");
		
		return userRepository.findById(id);
		
	}
	
	/**
	 * Get all users in DB
	 * @return list of user
	 */
	public List<Users> getUsers() {
		
		log.info("Fetching all users");
		
		return userRepository.findAll();
		
	}
	
	/**
	 * Save an user
	 * @param users
	 * @return user
	 */
	public Users saveUser(Users users) {
		
		if(users == null) {
			
			return null;
			
		}
		
		log.info("Saving new user {} to the DB", users.getUsername());
		
		users.setPassword(passwordEncoder.encode(users.getPassword()));
		
		return userRepository.save(users);
		
	}
	
	/**
	 * Update an user 
	 * @param user
	 * @return user
	 */
	public Users updateUser(Users user) {
		
		if(user == null) {
			
			return null;
			
		}
		
		return userRepository.save(user);
		
	}
	
	/**
	 * 
	 * @param id
	 */
	public void deleteUserById(Long id){
		
		userRepository.deleteById(id);
		
	}

}
