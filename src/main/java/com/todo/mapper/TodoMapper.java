package com.todo.mapper;

import com.todo.domain.Todo;
import com.todo.dto.TodoRequest;
import com.todo.dto.TodoResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.beans.BeanUtils.copyProperties;

@Component
public class TodoMapper {

  public Todo toEntity(TodoRequest request) {
    Todo entity = new Todo();
    copyProperties(request, entity);
    return entity;
  }

  public TodoResponse fromEntity(Todo entity) {
    TodoResponse response = new TodoResponse();
    copyProperties(entity, response);
    return response;
  }

  public List<TodoResponse> fromEntity(List<Todo> entities) {
    return entities
      .stream()
      .map(this::fromEntity)
      .collect(Collectors.toList());
  }

  public Page<TodoResponse> fromEntity(Page<Todo> entities) {
    return entities.map(this::fromEntity);
  }
}
