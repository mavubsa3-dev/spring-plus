package org.example.expert.domain.chat;

import org.example.expert.domain.chat.entity.ChatUser;
import org.example.expert.domain.chat.repository.ChatUserRepository;
import org.springframework.stereotype.Component;

@Component
public class TestInit {

	public TestInit(ChatUserRepository repository){
		repository.save(new ChatUser("Alice"));
		repository.save(new ChatUser("Bob"));
	}
}
