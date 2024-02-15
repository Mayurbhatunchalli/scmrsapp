package com.social.media.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.media.entity.Comment;
import com.social.media.entity.Post;
import com.social.media.entity.User;
import com.social.media.repo.CommentRepository;
import com.social.media.repo.PostRepository;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	public UserService userService;
	
	@Autowired
	public PostService postService;
	
	@Autowired
	public CommentRepository commentRepo;
	
	@Autowired
	public PostRepository postRepo;

	@Override
	public Comment createComment(Comment comment, Integer postId, Integer userId) throws Exception {

		User user = userService.findUser(userId);
		
		Post post = postService.findPostById(postId);
		
		comment.setUser(user);
		comment.setContent(comment.getContent());
		comment.setCreatedAt(LocalDateTime.now());
		
		Comment saved = commentRepo.save(comment);
		
		post.getComments().add(saved);
		
		postRepo.save(post);
		
		return saved;
	}

	@Override
	public Comment likeComment(Integer commentId, Integer userId) throws Exception {

		Comment comment = findCommentById(commentId);
		
		User user = userService.findUser(userId);
		
		if(!comment.getLiked().contains(user)) {
			comment.getLiked().add(user);
		} else {
			comment.getLiked().remove(user);
		}
		
		return commentRepo.save(comment);
	}

	@Override
	public Comment findCommentById(Integer commentId) throws Exception {

		Optional<Comment> comment = commentRepo.findById(commentId);
		
		if(comment.isEmpty()) {
			
			throw new Exception("Comment not found");
		}
		
		return comment.get();
	}

}
