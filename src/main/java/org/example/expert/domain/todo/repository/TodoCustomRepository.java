package org.example.expert.domain.todo.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.example.expert.domain.todo.dto.response.GetTodoResponseDto;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

public interface TodoCustomRepository {

	Optional<Todo> findByIdWithUser(@Param("todoId") Long todoId);

	Page<GetTodoResponseDto> findTodoByOption(
		String title,
		LocalDateTime startDate,
		String nickname,
		Pageable pageable
	);
}
