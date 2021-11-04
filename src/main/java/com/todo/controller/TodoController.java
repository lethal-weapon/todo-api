package com.todo.controller;

import com.todo.domain.Todo;
import com.todo.service.TodoService;
import com.todo.mapper.*;
import com.todo.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

  private final TodoService service;
  private final TodoMapper mapper;

  public TodoController(TodoService service,
                        TodoMapper mapper) {
    this.service = service;
    this.mapper = mapper;
  }

  @GetMapping
  public List<TodoResponse> getAll() {
    return mapper.fromEntity(
      service.findAll()
    );
  }

  @GetMapping("/{id}")
  public TodoResponse getById(@PathVariable long id) {
    return mapper.fromEntity(
      service.findById(id)
    );
  }

  @GetMapping(params = {"page", "pageSize"})
  public Page<TodoResponse> getByPage(@RequestParam int page,
                                      @RequestParam int pageSize) {
    return mapper.fromEntity(
      service.findByPaging(page, pageSize)
    );
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TodoResponse createTodo(@RequestBody TodoRequest request) {
    Todo unsaved = mapper.toEntity(request);
    Todo saved = service.addNewTodo(unsaved);
    return mapper.fromEntity(saved);
  }

  @PutMapping("/{id}")
  public TodoResponse updateTodo(@PathVariable long id,
                                 @RequestBody TodoRequest request) {
    Todo updated = mapper.toEntity(request);
    Todo saved = service.updateTodo(id, updated);
    return mapper.fromEntity(saved);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteTodo(@PathVariable long id) {
    service.deleteById(id);
  }
}
