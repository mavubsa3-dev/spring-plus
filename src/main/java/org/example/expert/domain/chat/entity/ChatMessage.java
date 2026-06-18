package org.example.expert.domain.chat.entity;

import java.time.LocalDateTime;

import org.example.expert.domain.chat.dto.ChatMessageDto;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ChatMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private ChatRoom chatRoom;

	@ManyToOne(fetch = FetchType.LAZY)
	private ChatUser sender;

	private String content;

	private LocalDateTime createdAt = LocalDateTime.now();

	public ChatMessage(ChatUser sender, ChatRoom chatRoom, String content){
		this.sender = sender;
		this.chatRoom = chatRoom;
		this.content = content;
		this.createdAt = LocalDateTime.now();
	}
}
