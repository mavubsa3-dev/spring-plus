package org.example.expert.domain.chat.service;
import java.util.List;

import org.example.expert.domain.chat.dto.ChatMessageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.example.expert.domain.chat.repository.ChatMessageRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatQueryService {
	private final ChatMessageRepository chatMessageRepository;

	public List<ChatMessageResponseDto> getRecentMessage(int size){
		Pageable pageable = PageRequest.of(0, size);

		return chatMessageRepository.findRecentMessage(pageable)
			.stream()
			.map(ChatMessageResponseDto::new)
			.toList();
	}

	public List<ChatMessageResponseDto> getMessageBefore(Long lastMessageId, int size){
		Pageable pageable = PageRequest.of(0, size);
		return chatMessageRepository.findMessageBefore(lastMessageId, pageable)
			.stream()
			.map(ChatMessageResponseDto::new)
			.toList();
	}

	public List<ChatMessageResponseDto> getRecentMessages(Long roomId, int size){
		Pageable pageable = PageRequest.of(0, size);

		return chatMessageRepository.findRecentByRoom(roomId, pageable)
			.stream()
			.map(ChatMessageResponseDto::new)
			.toList();
	}
}
