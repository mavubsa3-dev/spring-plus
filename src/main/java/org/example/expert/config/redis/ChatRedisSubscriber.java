package org.example.expert.config.redis;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatRedisSubscriber implements MessageListener {

	private final SimpMessagingTemplate messagingTemplate;
	private final ObjectMapper objectMapper;

	@Override
	public void onMessage(Message message, byte[] pattern) {
		try {
			RedisChatMessage redisChatMessage = objectMapper.readValue(
				message.getBody(),
				RedisChatMessage.class
			);

			messagingTemplate.convertAndSend("/sub/chat/" + redisChatMessage.getRoomId(), redisChatMessage);

		} catch (Exception e) {
			log.error("Redis 메시지 역직렬화 실패: {}", e.getMessage());
		}
	}
}
