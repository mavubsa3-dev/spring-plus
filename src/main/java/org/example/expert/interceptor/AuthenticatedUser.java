package org.example.expert.interceptor;

import java.security.Principal;

import org.example.expert.domain.chat.entity.ChatUser;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter

public class AuthenticatedUser implements Principal {
	private final ChatUser user;
	private final String name;

	public AuthenticatedUser(ChatUser user){
		this.user = user;
		this.name = user.getName();

	}

	public static ChatUser fromPrincipal(Principal principal){
		return ((AuthenticatedUser)principal).getUser();
	}

}
