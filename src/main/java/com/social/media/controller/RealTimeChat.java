package com.social.media.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.social.media.entity.Message;

@Controller
public class RealTimeChat {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
//	@MessageMapping("/chat/{groupId}")
//	public Message sendToUser(@Payload Message message, @DestinationVariable String groupId) {
//		
//		simpMessagingTemplate.convertAndSendToUser(groupId, "/private", message);
//		
//		return message;
//	}
//	
//	@MessageMapping("/chat/{groupId}")
//	@SendTo("/chat/public")
//	public Message sendToUserPublic(@Payload Message message) {
//		
//		return message;
//	}

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message receiveMessage(@Payload Message message){
        return message;
    }

    @MessageMapping("/chat/{groupId}")
    public Message recMessage(@Payload Message message, @DestinationVariable String groupId){
        simpMessagingTemplate.convertAndSendToUser(groupId,"/private",message);
        System.out.println(message.toString());
        return message;
    }
	
}
