package com.todo.service;

import com.todo.domain.Todo;
import com.todo.data.TodoRepository;
import com.todo.exception.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

  private final TodoRepository repo;

  public TodoService(TodoRepository repo) {
    this.repo = repo;
  }

  public List<Todo> findAll() {
    return (List<Todo>) repo.findAll();
  }

  public Todo findById(long id) {
    return repo
      .findById(id)
      .orElseThrow(EntityNotFoundException::new);
  }

  public Page<Todo> findByPaging(int page,
                                 int pageSize) {
    PageRequest pageable = PageRequest
      .of(page, pageSize, Sort.by("id").descending());

    return repo.findAll(pageable);
  }

  public Todo addNewTodo(Todo unsaved) {
    unsaved.setId(null);
    return repo.save(unsaved);
  }

  public Todo updateTodo(long pathId,
                         Todo updated) {
    if (updated.getId() == null) {
      throw new EntityIdNotExistedException();
    }
    if (!updated.getId().equals(pathId)) {
      throw new EntityIdNotMatchException();
    }
    if (repo.findById(pathId).isEmpty()) {
      throw new EntityNotFoundException();
    }

    return repo.save(updated);
  }

  public void deleteById(long id) {
    try {
      repo.deleteById(id);
    } catch (EmptyResultDataAccessException ignored) {
    }
  }
}
