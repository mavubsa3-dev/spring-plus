package org.example.expert.domain.chat.dto;

import java.time.LocalDateTime;

import org.example.expert.domain.chat.entity.ChatMessage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChatMessageResponseDto {

	private Long messageId;
	private String content;
	private Long senderId;
	private String senderName;
	private LocalDateTime createdAt;

	public ChatMessageResponseDto(ChatMessage message){
		this.messageId = message.getId();
		this.content = message.getContent();
		this.senderId = message.getSender().getId();
		this.senderName = message.getSender().getName();
		this.createdAt = message.getCreatedAt();
	}
}
