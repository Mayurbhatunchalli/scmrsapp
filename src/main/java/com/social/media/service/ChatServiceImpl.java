package com.social.media.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.media.entity.Chat;
import com.social.media.entity.User;
import com.social.media.repo.ChatRepository;

@Service
public class ChatServiceImpl implements ChatService {

	@Autowired
	public ChatRepository chatRepo;
	
	@Override
	public Chat createChat(User reqUser, User user) {
		
		Chat isExists = chatRepo.findChatByUserId(user, reqUser);
		
		if(isExists != null) {
			return isExists;
		}
		
		Chat chat = new Chat();
		
		chat.getUsers().add(user);
		chat.getUsers().add(reqUser);
		chat.setTimestamp(LocalDateTime.now());
		
		return chatRepo.save(chat);
	}

	@Override
	public Chat findByChatId(Integer chatId) throws Exception {
		Optional<Chat> opt = chatRepo.findById(chatId);
		
		if(opt.isEmpty()) {
			throw new Exception("Chat not exists");
		}
		
		return opt.get();
	}

	@Override
	public List<Chat> findUsersChat(Integer userId) {
		
		return chatRepo.findByUsersId(userId);
	}

}
