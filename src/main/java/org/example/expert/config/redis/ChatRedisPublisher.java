package org.example.expert.config.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatRedisPublisher {
	private final RedisTemplate<String, Object> redisTemplate;

	public void publish(Long roomId, RedisChatMessage message){
		String topic = "chat-room: " + roomId;
		redisTemplate.convertAndSend(topic, message);
	}
}
