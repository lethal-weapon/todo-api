package com.todo.advice;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorResponse {

  private Integer code;
  private String message;

  public ErrorResponse(Integer code, String message) {
    this.code = code;
    this.message = message;
  }
}
