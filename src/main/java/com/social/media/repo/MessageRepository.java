package com.social.media.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.social.media.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {
	
	public List<Message> findByChatId(Integer chatId);

}
