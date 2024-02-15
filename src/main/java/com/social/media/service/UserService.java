package com.social.media.service;

import java.util.List;

import com.social.media.entity.User;
import com.social.media.exceptions.UserException;

public interface UserService {
	
	public List<User> findByQuery(String query);

	public User findUser(int id) throws UserException;

	public List<User> allUser();
	
	public User updateUser(User user, Integer userId) throws UserException;
	
	public User findUserByEmail(String email);

	public User findUserByToken(String jwt);

	public User updateNewUser(User user, Integer userId) throws UserException;
	
	public User followUser(Integer user2, User user) throws UserException;

}
