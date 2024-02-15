package com.social.media.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.social.media.dto.UserDTO;
import com.social.media.entity.Post;
import com.social.media.entity.User;
import com.social.media.response.ApiResponse;
import com.social.media.service.PostService;
import com.social.media.service.UserService;

@RestController
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private UserService userService;

	@GetMapping("/api/posts/{postId}")
	public ResponseEntity<Post> getPostById(@PathVariable Integer postId) throws Exception {

		Post post = postService.findPostById(postId);

		return new ResponseEntity<>(post, HttpStatus.OK);
	}

	@GetMapping("/api/allPosts")
	public ResponseEntity<List<Post>> getAllPosts() throws Exception {

		List<Post> posts = postService.findAllPost();

		return new ResponseEntity<>(posts, HttpStatus.OK);
	}

	@GetMapping("/api/posts/user/{userId}")
	public ResponseEntity<List<Post>> getUserAllPosts(@PathVariable("userId") Integer id) throws Exception {

		List<Post> posts = postService.findPostByUserId(id);

		return new ResponseEntity<>(posts, HttpStatus.OK);
	}

	@GetMapping("/api/posts/saved/{userId}")
	public ResponseEntity<List<Post>> getUserSavedPosts(@PathVariable("userId") Integer id) throws Exception {

		List<Post> posts = postService.findSavedPostByUserId(id);

		return new ResponseEntity<>(posts, HttpStatus.OK);
	}

	@PostMapping("/api/posts")
	public ResponseEntity<Post> createPost(@RequestHeader("Authorization") String jwt, @RequestBody Post post)
			throws Exception {

		User user = userService.findUserByToken(jwt);

		Post newPost = postService.createNewPost(post, user.getId());

		return new ResponseEntity<>(newPost, HttpStatus.ACCEPTED);
	}

	@DeleteMapping("api/posts/delete/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId") Integer postId,
			@RequestHeader("Authorization") String jwt) throws Exception {

		User user = userService.findUserByToken(jwt);

		String message = postService.deletePost(postId, user.getId());

		ApiResponse res = new ApiResponse(message, true);

		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@PutMapping("/api/post/{postId}/user/{userId}")
	public ResponseEntity<UserDTO> savePost(@PathVariable("postId") Integer postId,
			@PathVariable("userId") Integer userId) throws Exception {

		User user = postService.savePost(postId, userId);

		UserDTO userDto = new UserDTO();
		userDto.setId(user.getId());
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setEmail(user.getEmail());
		userDto.setSavedPost(user.getSavedPost());

		return new ResponseEntity<>(userDto, HttpStatus.OK);
	}

	@PutMapping("/api/posts/like/{postId}")
	public ResponseEntity<Post> likePost(@PathVariable("postId") Integer postId,
			@RequestHeader("Authorization") String jwt) throws Exception {

		User user = userService.findUserByToken(jwt);

		Post post = postService.likePost(postId, user.getId());

		return new ResponseEntity<>(post, HttpStatus.OK);
	}

}
