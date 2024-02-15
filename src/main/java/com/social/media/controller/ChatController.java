package com.social.media.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.social.media.entity.Chat;
import com.social.media.entity.User;
import com.social.media.exceptions.UserException;
import com.social.media.request.CreateChatRequest;
import com.social.media.service.ChatService;
import com.social.media.service.UserService;

@RestController
public class ChatController {

	@Autowired
	private ChatService chatService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/api/chats/user")
	public Chat createChat(@RequestHeader("Authorization") String jwt, 
			@RequestBody CreateChatRequest reqUserId) throws UserException {
		
		User user = userService.findUserByToken(jwt);
		
		User reqUser = userService.findUser(reqUserId.getUserId());
		
		return chatService.createChat(user, reqUser);
		
	}
	
	@GetMapping("/api/chats")
	public List<Chat> findUsersChat(@RequestHeader("Authorization") String jwt) {
		
		User user = userService.findUserByToken(jwt);
		
		return chatService.findUsersChat(user.getId());
		
	}
}
