package org.example.expert.domain.chat.controller;

import java.util.List;

import org.example.expert.domain.chat.entity.ChatRoom;
import org.example.expert.domain.chat.repository.ChatRoomRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/chat/rooms")
public class ChatRoomController {
	private final ChatRoomRepository chatRoomRepository;


	@GetMapping
	public List<ChatRoom> getAll() {
		return chatRoomRepository.findAll();
	}

	@PostMapping
	public ChatRoom create(@RequestParam String name){
		ChatRoom chatRoom = new ChatRoom(name);
		return chatRoomRepository.save(chatRoom);
	}
}
