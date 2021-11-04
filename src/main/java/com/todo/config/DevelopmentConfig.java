package com.todo.config;

import com.todo.data.TodoRepository;
import com.todo.domain.Todo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Profile("dev")
@Configuration
public class DevelopmentConfig {

  @Bean
  public CommandLineRunner loadData(TodoRepository todoRepo) {
    return args -> {
      todoRepo.deleteAll();

      Todo todoA = new Todo("Write docs", false);
      Todo todoB = new Todo("Write test", false);
      Todo todoC = new Todo("Write code", true);
      Todo todoD = new Todo("Team Planning", true);

      todoRepo.saveAll(Arrays.asList(
        todoD,
        todoC,
        todoB,
        todoA
      ));
    };
  }
}
