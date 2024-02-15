package com.social.media.service;

import com.social.media.entity.Comment;
import com.social.media.exceptions.UserException;

public interface CommentService {
	
	Comment createComment(Comment comment, Integer postId, Integer userId) throws UserException, Exception;
	
	Comment likeComment(Integer comment, Integer userId) throws Exception;
	
	Comment findCommentById(Integer commentId) throws Exception;

}
