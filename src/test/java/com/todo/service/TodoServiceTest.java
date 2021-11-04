package com.todo.service;

import com.todo.data.TodoRepository;
import com.todo.domain.Todo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.mockito.Mock;
import org.mockito.InjectMocks;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(SpringExtension.class)
class TodoServiceTest {

  @Mock
  TodoRepository repo;

  @InjectMocks
  TodoService service;

  @Test
  void shouldReturnAllTodosWhenFindAll() {
    Iterable<Todo> todos = Arrays.asList(
      new Todo("Build UI", false),
      new Todo("Build API", true)
    );
    given(repo.findAll()).willReturn(todos);

    Iterable<Todo> actual = service.findAll();

    assertIterableEquals(todos, actual);
  }

  @Test
  void shouldReturnOneTodoWhenFindById() {
    Todo todo = new Todo("Play Snooker", true);
    todo.setId(4342L);
    given(repo.findById(anyLong())).willReturn(Optional.of(todo));

    Todo actual = service.findById(todo.getId());

    assertSame(todo, actual);
  }

  @Test
  void shouldCreateTodoWhenAddNewTodo() {
    Todo unsaved = new Todo("Brush teeth", false);
    Todo saved = new Todo("Brush teeth", false);
    saved.setId(4322L);
    given(repo.save(any(Todo.class))).willReturn(saved);

    Todo actual = service.addNewTodo(unsaved);

    assertNotNull(actual.getId());
    assertEquals(unsaved.getText(), actual.getText());
    assertEquals(unsaved.getDone(), actual.getDone());
  }

  @Test
  void shouldUpdateTodoWhenGivenAnUpdatedTodo() {
    Todo updated = new Todo("Play Tennis", false);
    updated.setId(4322L);
    given(repo.findById(anyLong())).willReturn(Optional.of(updated));
    given(repo.save(any(Todo.class))).willReturn(updated);

    Todo actual = service.updateTodo(updated.getId(), updated);

    assertEquals(updated.getId(), actual.getId());
    assertEquals(updated.getText(), actual.getText());
    assertEquals(updated.getDone(), actual.getDone());
  }

  @Test
  void shouldDeleteTodoWhenGivenTodoId() {
    willDoNothing().given(repo).deleteById(anyLong());

    service.deleteById(123L);

    verify(repo).deleteById(anyLong());
    verifyNoMoreInteractions(repo);
  }
}
