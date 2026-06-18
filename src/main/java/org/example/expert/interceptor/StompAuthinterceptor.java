package org.example.expert.interceptor;

import org.example.expert.config.JwtUtil;
import org.example.expert.domain.chat.entity.ChatUser;
import org.example.expert.domain.chat.repository.ChatUserRepository;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;


import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StompAuthinterceptor implements ChannelInterceptor {
	private final JwtUtil jwtUtil;
	private final ChatUserRepository userRepository;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel){
		StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

		if(StompCommand.CONNECT.equals(accessor.getCommand())){
			String token = accessor.getFirstNativeHeader("Authorization")
				.replace("Bearer ","");

			Long userId = jwtUtil.getUserId(token);

			ChatUser user = userRepository.findById(userId).orElseThrow(
				() -> new IllegalArgumentException("존재하는 사용자가 없습니다.")
			);

			accessor.setUser(new AuthenticatedUser(user));
		}
		return message;
	}

}
