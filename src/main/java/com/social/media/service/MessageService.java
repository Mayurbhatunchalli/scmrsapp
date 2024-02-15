package com.social.media.service;

import java.util.List;

import com.social.media.entity.Chat;
import com.social.media.entity.Message;
import com.social.media.entity.User;

public interface MessageService {
	
	public Message createMessage(User user, Integer chatId, Message message) throws Exception;
	
	public List<Message> findChatsMessages(Integer chatId) throws Exception;

}
