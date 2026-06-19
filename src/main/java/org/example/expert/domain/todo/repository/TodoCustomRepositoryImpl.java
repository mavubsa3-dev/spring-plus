package org.example.expert.domain.todo.repository;

import static java.time.Clock.*;
import static org.example.expert.domain.comment.entity.QComment.*;
import static org.example.expert.domain.manager.entity.QManager.*;
import static org.example.expert.domain.todo.entity.QTodo.*;
import static org.example.expert.domain.user.entity.QUser.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.example.expert.domain.manager.entity.Manager;
import org.example.expert.domain.todo.dto.response.GetTodoResponseDto;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TodoCustomRepositoryImpl implements TodoCustomRepository{

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Optional<Todo> findByIdWithUser(Long todoId) {
		Todo result =  jpaQueryFactory
			.selectFrom(todo)
			.join(todo.user,user).fetchJoin()
			.where(todo.id.eq(todoId))
			.fetchOne();

		return Optional.ofNullable(result);
	}

	@Override
	public Page<GetTodoResponseDto> findTodoByOption(String title, LocalDateTime startDate, String nickname, Pageable pageable) {
		List<GetTodoResponseDto> result = jpaQueryFactory
			.select(Projections.constructor(GetTodoResponseDto.class,
				todo.title,
				manager.countDistinct(),
				comment.countDistinct()))
			.from(todo)
			.leftJoin(todo.comments, comment)
			.leftJoin(todo.managers, manager)
			.where(
				todoTitleContains(title),
				todoManagerNicknameContains(nickname),
				todoCreatedAt(startDate)
			)
			.groupBy(todo.id)
			.orderBy(todo.createdAt.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Long> countQuery = jpaQueryFactory
			.select(todo.countDistinct())
			.from(todo)
			.leftJoin(todo.managers, manager)
			.where(
				todoTitleContains(title),
				todoCreatedAt(startDate),
				todoManagerNicknameContains(nickname)
			);

		return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
	}

	private BooleanExpression todoTitleContains(String title){
		return title != null ? todo.title.contains(title) : null;
	}

	private BooleanExpression todoManagerNicknameContains(String nickname){
		return nickname != null ? manager.user.nickname.contains(nickname) : null;
	}

	private BooleanExpression todoCreatedAt(LocalDateTime startDate){
		return startDate != null ? todo.createdAt.goe(startDate) : null;
	}
}
