package com.social.media.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.social.media.entity.Comment;
import com.social.media.entity.User;
import com.social.media.exceptions.UserException;
import com.social.media.service.CommentService;
import com.social.media.service.UserService;

@RestController
public class CommentController {

	@Autowired
	public CommentService commentService;
	
	@Autowired
	public UserService userService;
	
	@PostMapping("/api/comments/post/{postId}")
	public Comment createComment(@RequestBody Comment content, 
			@RequestHeader("Authorization") String jwt, @PathVariable("postId") Integer postId) 
					throws UserException, Exception {
		
		User user = userService.findUserByToken(jwt);
		
		Comment comment = commentService.createComment(content, postId, user.getId());
		
		return comment;
	}
	
	@PostMapping("/api/comments/like/{commentId}")
	public Comment likeComment(@RequestHeader("Authorization") String jwt, 
			@PathVariable("commentId") Integer commentId) throws UserException, Exception {
		
		User user = userService.findUserByToken(jwt);
		
		Comment likedComment = commentService.likeComment(commentId, user.getId());
		
		return likedComment;
	}
	
}
