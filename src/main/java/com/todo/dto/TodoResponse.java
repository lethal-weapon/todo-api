package com.todo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TodoResponse {

  private Long id;
  private String text;
  private Boolean done;

}
