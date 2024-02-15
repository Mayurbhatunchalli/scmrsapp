package com.social.media.service;

import java.util.List;

import com.social.media.entity.Post;
import com.social.media.entity.User;
import com.social.media.exceptions.UserException;

public interface PostService {

	Post createNewPost(Post post, Integer userId) throws Exception;
	
	String deletePost(Integer postId, Integer userId) throws Exception;
	
	List<Post> findPostByUserId(Integer userId);
	
	Post findPostById(Integer postId) throws Exception;
	
	List<Post> findAllPost();
	
	User savePost(Integer postId, Integer userId) throws Exception;
	
	Post likePost(Integer postId, Integer userId) throws Exception;

	List<Post> findSavedPostByUserId(Integer userId) throws UserException;
	
}
