package org.example.expert.domain.todo.dto.response;

import lombok.Getter;

@Getter
public class GetTodoResponseDto {
	private String content;
	private int managerCount;
	private int commentCount;

	public GetTodoResponseDto(String content, int managerCount, int commentCount){
		this.content = content;
		this.managerCount = managerCount;
		this.commentCount = commentCount;
	}
}
