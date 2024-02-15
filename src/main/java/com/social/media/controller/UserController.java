package com.social.media.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.social.media.entity.User;
import com.social.media.exceptions.UserException;
import com.social.media.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping("{userId}")
	public User getUser(@PathVariable("userId") int id) throws UserException {
		
		return userService.findUser(id);
	}
	
	@GetMapping("/allUsers")
	public List<User> allUser() {
		
		List<User> users = userService.allUser();
		
		return users;
	}
	
	@GetMapping("/query")
	public List<User> searchUser(@RequestParam("query") String query) {
		
		return userService.findByQuery(query);
	}
	
	@GetMapping("/api/byEmail/{email}")
	public User findByUser(@PathVariable String email) {
		
		return userService.findUserByEmail(email);
	}
	
	@GetMapping("/profile")
	public User getUserFromToken(@RequestHeader("Authorization") String jwt) {
		
		User user = userService.findUserByToken(jwt);
		
		user.setPassword(null);
		
		return user;
	}
	
	@PutMapping("/profile/update")
	public User updateUser(@RequestHeader("Authorization") String jwt, @RequestBody User newUser) throws UserException {
		
		User user = userService.findUserByToken(jwt);
		
		return userService.updateNewUser(newUser, user.getId());

	}
	
	@PutMapping("/follow/{userId}")
	public User followUser(@RequestHeader("Authorization") String jwt, @PathVariable Integer userId) throws UserException {
		
		User user = userService.findUserByToken(jwt);
		
		return userService.followUser(userId, user);

	}
}
