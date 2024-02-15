package com.social.media.service;

import java.util.List;

import com.social.media.entity.Chat;
import com.social.media.entity.User;

public interface ChatService {
	
	public Chat createChat(User reqUser, User user);
	
	public Chat findByChatId(Integer chatId) throws Exception;
	
	public List<Chat> findUsersChat(Integer userId);

}
