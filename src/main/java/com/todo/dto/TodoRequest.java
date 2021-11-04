package com.todo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TodoRequest {

  private Long id;
  private String text;
  private Boolean done;

}
