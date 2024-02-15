package com.social.media.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social.media.entity.Message;
import com.social.media.entity.User;
import com.social.media.service.MessageService;
import com.social.media.service.UserService;

@RestController
@RequestMapping("/api/chat")
public class MessageController {

	@Autowired
	private MessageService messageService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/message/{chatId}")
	public Message createMessage(@RequestHeader("Authorization") String jwt, 
			@RequestBody Message message, @PathVariable("chatId") Integer chatId) throws Exception {
		
		User user = userService.findUserByToken(jwt);
		
		Message createMessage = messageService.createMessage(user, chatId, message);
		
		return createMessage;
	}
	
	@GetMapping("/messages/{chatId}")
	public List<Message> findChatMessages(@RequestHeader("Authorization") String jwt, 
			@PathVariable("chatId") Integer chatId) throws Exception {
		
		List<Message> messages = messageService.findChatsMessages(chatId);
		
		return messages;
	}
}
