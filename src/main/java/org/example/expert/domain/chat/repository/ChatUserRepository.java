package org.example.expert.domain.chat.repository;

import org.example.expert.domain.chat.entity.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatUserRepository extends JpaRepository<ChatUser, Long> {
}
