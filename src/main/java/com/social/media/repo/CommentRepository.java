package com.social.media.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.social.media.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
