package com.todo.controller;

import com.todo.data.TodoRepository;
import com.todo.domain.Todo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class TodoControllerTest {

  static final String BASE_URL = "/todos";

  @Autowired
  MockMvc mock;

  @Autowired
  TodoRepository repo;

  @Autowired
  ObjectMapper jsonMapper;

  @BeforeEach
  void setUp() {
    repo.deleteAll();
  }

  @AfterEach
  void tearDown() {
    repo.deleteAll();
  }

  @Test
  void shouldReturnAllTodosWhenGetAllGivenMultipleTodos() throws Exception {
    Todo saved1 = repo.save(new Todo("Finish Work", true));
    Todo saved2 = repo.save(new Todo("Hotpot Dinner", false));
    String url = String.format("%s", BASE_URL);

    ResultActions result = mock.perform(get(url));

    result
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").isNotEmpty())
      .andExpect(jsonPath("$").isArray())
      .andExpect(jsonPath("$[0].id").value(saved1.getId()))
      .andExpect(jsonPath("$[0].text").value(saved1.getText()))
      .andExpect(jsonPath("$[0].done").value(saved1.getDone()))
      .andExpect(jsonPath("$[1].id").value(saved2.getId()))
      .andExpect(jsonPath("$[1].text").value(saved2.getText()))
      .andExpect(jsonPath("$[1].done").value(saved2.getDone()))
      .andExpect(jsonPath("$[2]").doesNotExist())
    ;
  }

  @Test
  void shouldReturnOneTodoWhenGetOneGivenSingleTodo() throws Exception {
    Todo saved = repo.save(new Todo("Tea Time", false));
    String url = String.format("%s/%d", BASE_URL, saved.getId());

    ResultActions result = mock.perform(get(url));

    result
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").isNotEmpty())
      .andExpect(jsonPath("$.id").value(saved.getId()))
      .andExpect(jsonPath("$.text").value(saved.getText()))
      .andExpect(jsonPath("$.done").value(saved.getDone()))
    ;
  }

  @Test
  void shouldReturnPagedTodosWhenGetByPageGivenPageAndPageSize() throws Exception {
    Todo saved1 = repo.save(new Todo("Wake up", false));
    Todo saved2 = repo.save(new Todo("Bathroom", true));
    Todo saved3 = repo.save(new Todo("Go to work", true));
    String url = String.format("%s?page=%d&pageSize=%d", BASE_URL, 1, 2);

    ResultActions result = mock.perform(get(url));

    result
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").isNotEmpty())
      .andExpect(jsonPath("$.content").isNotEmpty())
      .andExpect(jsonPath("$.content").isArray())
      .andExpect(jsonPath("$.content[0].id").value(saved1.getId()))
      .andExpect(jsonPath("$.content[0].text").value(saved1.getText()))
      .andExpect(jsonPath("$.content[0].done").value(saved1.getDone()))
      .andExpect(jsonPath("$.content[1]").doesNotExist())
    ;
  }

  @Test
  void shouldCreateTodoWhenPostGivenATodo() throws Exception {
    Todo unsaved = new Todo("Lunch break", false);
    String url = String.format("%s", BASE_URL);
    assertEquals(0L, repo.count());

    ResultActions result = mock.perform(
      post(url)
        .contentType(APPLICATION_JSON)
        .content(jsonMapper.writeValueAsString(unsaved))
    );

    assertEquals(1L, repo.count());
    result
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$").isNotEmpty())
      .andExpect(jsonPath("$.id").isNumber())
      .andExpect(jsonPath("$.text").value(unsaved.getText()))
      .andExpect(jsonPath("$.done").value(unsaved.getDone()))
    ;
  }

  @Test
  void shouldUpdateTodoWhenPutGivenAnUpdatedTodo() throws Exception {
    Todo unsaved = new Todo("Repair phone", false);
    Todo updated = repo.save(unsaved);
    updated.setText("Repair iPhone");
    updated.setDone(true);
    String url = String.format("%s/%d", BASE_URL, updated.getId());

    ResultActions result = mock.perform(
      put(url)
        .contentType(APPLICATION_JSON)
        .content(jsonMapper.writeValueAsString(updated))
    );

    result
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").isNotEmpty())
      .andExpect(jsonPath("$.id").value(updated.getId()))
      .andExpect(jsonPath("$.text").value(updated.getText()))
      .andExpect(jsonPath("$.done").value(updated.getDone()))
    ;
  }

  @Test
  void shouldDeleteTodoWhenGivenTodoId() throws Exception {
    Todo saved = repo.save(new Todo("Watch TV", true));
    String url = String.format("%s/%d", BASE_URL, saved.getId());
    assertEquals(1L, repo.count());

    ResultActions result = mock.perform(delete(url));

    assertEquals(0L, repo.count());
    result
      .andExpect(status().isNoContent())
      .andExpect(jsonPath("$").doesNotExist())
    ;
  }
}
