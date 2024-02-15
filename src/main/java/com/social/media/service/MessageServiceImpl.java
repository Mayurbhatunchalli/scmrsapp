package com.social.media.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.media.entity.Chat;
import com.social.media.entity.Message;
import com.social.media.entity.User;
import com.social.media.repo.ChatRepository;
import com.social.media.repo.MessageRepository;

@Service
public class MessageServiceImpl implements MessageService {
	
	@Autowired
	private MessageRepository messageRepo;
	
	@Autowired
	private ChatService chatService;
	
	@Autowired
	private ChatRepository chatRepo;

	@Override
	public Message createMessage(User user, Integer chatId, Message reqMessage) throws Exception {
		
		Chat chat = chatService.findByChatId(chatId);

		Message msg = new Message();
		
		msg.setChat(chat);
		msg.setContent(reqMessage.getContent());
		msg.setImage(reqMessage.getImage());
		msg.setUser(user);
		msg.setTime(LocalDateTime.now());
		
		Message savedMessage = messageRepo.save(msg);
		
		chat.getMessages().add(savedMessage);
		
		chatRepo.save(chat);
		
		return savedMessage;
	}

	@Override
	public List<Message> findChatsMessages(Integer chatId) throws Exception {
		
		return messageRepo.findByChatId(chatId);
	}

}
