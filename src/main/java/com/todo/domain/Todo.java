package com.todo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class Todo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String text;
  private Boolean done;

  public Todo(String text,
              Boolean done) {
    this.text = text;
    this.done = done;
  }
}
