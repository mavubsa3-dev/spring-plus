package org.example.expert.domain.chat.controller;

import java.util.List;

import org.example.expert.domain.chat.dto.ChatMessageResponseDto;
import org.example.expert.domain.chat.service.ChatQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatQueryController {

	private final ChatQueryService chatQueryService;

	@GetMapping("/messages")
	public List<ChatMessageResponseDto> getMessages(
		@RequestParam(defaultValue = "50") int size){
		return chatQueryService.getRecentMessage(size);
	}

	@GetMapping("/messages/before/{id}")
	public List<ChatMessageResponseDto> getMessagesBefore
		(	@PathVariable long id,
			@RequestParam(defaultValue = "50") int size){
		return chatQueryService.getMessageBefore(id, size);
	}

	@GetMapping("/messages/{roomId}/messages")
	public List<ChatMessageResponseDto> getMessages(
		@PathVariable Long roomId, @RequestParam(defaultValue = "50") int size) {
		return chatQueryService.getRecentMessages(roomId, size);
	}


}
