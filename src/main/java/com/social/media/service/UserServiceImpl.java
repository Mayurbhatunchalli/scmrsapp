package com.social.media.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.social.media.config.JwtProvider;
import com.social.media.entity.User;
import com.social.media.exceptions.UserException;
import com.social.media.repo.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User findUserByEmail(String email) {

		return userRepo.findByEmail(email);
	}

	@Override
	public User findUser(int id) throws UserException {

		Optional<User> user = userRepo.findById(id);

		if (user.isEmpty()) {

			throw new UserException("User not found for the Id: " + id);
		}

		User user1 = user.get();

		return user1;
	}

	@Override
	public List<User> findByQuery(String query) {

		return userRepo.findUserByQuery(query);

	}

	@Override
	public User updateUser(User user, Integer userId) throws UserException {

		Optional<User> user1 = userRepo.findById(userId);

		if (user1.isEmpty()) {

			throw new UserException("User not found for the Id : " + userId);
		}

		User oldUser = user1.get();

		if (user.getFirstName() != null) {
			oldUser.setFirstName(user.getFirstName());
		}

		if (user.getLastName() != null) {
			oldUser.setLastName(user.getLastName());
		}

		if (user.getEmail() != null) {
			oldUser.setEmail(user.getEmail());
		}

		if (user.getPassword() != null) {
			oldUser.setPassword(passwordEncoder.encode(user.getPassword()));
		}

		User newUser = userRepo.save(oldUser);

		return newUser;
	}

	@Override
	public User updateNewUser(User user, Integer userId) throws UserException {

		Optional<User> user1 = userRepo.findById(userId);

		if (user1.isEmpty()) {

			throw new UserException("User not found for the Id : " + userId);
		}

		User oldUser = user1.get();

		if (user.getFirstName() != null) {
			oldUser.setFirstName(user.getFirstName());
		}

		if (user.getLastName() != null) {
			oldUser.setLastName(user.getLastName());
		}
		
		if (user.getProfilePicture() != null) {
			oldUser.setProfilePicture(user.getProfilePicture());
		}

		User newUser = userRepo.save(oldUser);

		return newUser;
	}

	@Override
	public List<User> allUser() {

		return userRepo.findAll();
	}

	@Override
	public User findUserByToken(String jwt) {

		String email = JwtProvider.getEmailFromJwtToken(jwt);

		User user = userRepo.findByEmail(email);

		return user;
	}

	@Override
	public User followUser(Integer userId1, User user) throws UserException {

		Optional<User> user2 = userRepo.findById(userId1);

		if (user2.isEmpty()) {

			throw new UserException("User not found for the Id : " + userId1);
		}
		
		User followUser = user2.get();
		
		if(user.getId() == followUser.getId()) {
			System.out.println("Not able to follow the same user");
		} else {
			if(user.getFollowings().contains(followUser.getId())) {
				user.getFollowings().remove(followUser.getId());
			} else {
				user.getFollowings().add(followUser.getId());
			}
			
			if(followUser.getFollowers().contains(user.getId())) {
				followUser.getFollowers().remove(user.getId());
			} else {
				followUser.getFollowers().add(user.getId());
			}
		}
		
		userRepo.save(user);
		userRepo.save(followUser);

		return user;
	}
}
