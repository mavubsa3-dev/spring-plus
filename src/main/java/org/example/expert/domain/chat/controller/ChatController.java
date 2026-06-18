package org.example.expert.domain.chat.controller;

import java.security.Principal;

import org.example.expert.config.redis.ChatRedisPublisher;
import org.example.expert.config.redis.RedisChatMessage;
import org.example.expert.domain.chat.dto.ChatMessageDto;
import org.example.expert.domain.chat.entity.ChatMessage;
import org.example.expert.domain.chat.entity.ChatRoom;
import org.example.expert.domain.chat.entity.ChatUser;
import org.example.expert.domain.chat.repository.ChatMessageRepository;
import org.example.expert.domain.chat.repository.ChatRoomRepository;
import org.example.expert.domain.chat.repository.ChatUserRepository;
import org.example.expert.interceptor.AuthenticatedUser;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController {

	private final SimpMessagingTemplate messagingTemplate;
	private final ChatRedisPublisher chatRedisPublisher;
	private final ChatMessageRepository chatMessageRepository;
	private final ChatRoomRepository chatRoomRepository;

	@MessageMapping("/chat.send")
	public void send(ChatMessageDto messageDto, Principal principal){
		ChatRoom chatRoom = chatRoomRepository.findById(messageDto.getRoomId())
			.orElseThrow(
				() -> new IllegalArgumentException("채팅방이 존재하지 않습니다.")
			);

		ChatUser sender = AuthenticatedUser.fromPrincipal(principal);

		ChatMessage message = new ChatMessage(sender, chatRoom, messageDto.getContent());
		chatMessageRepository.save(message);
		RedisChatMessage redisChatMessage = new RedisChatMessage(
			message.getChatRoom().getId(),
			message.getSender().getId(),
			message.getSender().getName(),
			message.getContent()
		);
		messagingTemplate.convertAndSend("/sub/chat/" + chatRoom.getId(), messageDto);
		chatRedisPublisher.publish(chatRoom.getId(), redisChatMessage);
	}
}
