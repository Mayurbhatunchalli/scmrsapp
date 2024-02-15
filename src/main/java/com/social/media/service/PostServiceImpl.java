package com.social.media.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.social.media.dto.UserDTO;
import com.social.media.entity.Post;
import com.social.media.entity.User;
import com.social.media.exceptions.UserException;
import com.social.media.repo.PostRepository;
import com.social.media.repo.UserRepository;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	PostRepository postRepo;

	@Autowired
	UserService userService;

	@Autowired
	UserRepository userRepo;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public Post createNewPost(Post post, Integer userId) throws Exception {

		User user = userService.findUser(userId);
		
		Post newPost = new Post();

		newPost.setCaption(post.getCaption());
		newPost.setImage(post.getImage());
		newPost.setVideo(post.getVideo());
		newPost.setCreatedAt(LocalDate.now());
		newPost.setUser(user);

		return postRepo.save(newPost);
	}

	@Override
	public String deletePost(Integer postId, Integer userId) throws Exception {

		Post post = findPostById(postId);

		User user = userService.findUser(userId);

		if (post.getUser().getId() != user.getId()) {

			throw new Exception("You can't delete another user's post");
		}

		postRepo.delete(post);

		return "Post deleted successfully";
	}

	@Override
	public List<Post> findPostByUserId(Integer userId) {

		return postRepo.findPostByUserId(userId);
	}
	
	@Override
	public List<Post> findSavedPostByUserId(Integer userId) throws UserException {
		
		User user = userService.findUser(userId);

		return user.getSavedPost();
	}

	@Override
	public Post findPostById(Integer postId) throws Exception {

		Optional<Post> post = postRepo.findById(postId);

		if (post.isEmpty()) {

			throw new Exception("Post is not found for the ID: " + postId);
		}
		return post.get();
	}

	@Override
	public List<Post> findAllPost() {

		return postRepo.findAll();
	}

	@Override
	public User savePost(Integer postId, Integer userId) throws Exception {

		Post post = findPostById(postId);

		User user = userService.findUser(userId);

		if (user.getSavedPost().contains(post)) {
			user.getSavedPost().remove(post);
		} else {
			user.getSavedPost().add(post);
		}

		userRepo.save(user);

		return user;
	}

	@Override
	public Post likePost(Integer postId, Integer userId) throws Exception {

		Post post = findPostById(postId);

		User user = userService.findUser(userId);
		
		if (post.getLiked().contains(user)) {
			post.getLiked().remove(user);
		} else {
			post.getLiked().add(user);
		}

		return postRepo.save(post);
	}

}
