package com.todo.advice;

import com.todo.exception.*;
import com.todo.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ControllerAdvice {

  @ExceptionHandler({
    EntityNotFoundException.class
  })
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponse entityNotFoundExceptionHandler(Exception exception) {
    return new ErrorResponse(404, exception.getMessage());
  }

  @ExceptionHandler({
    EntityIdNotExistedException.class,
    EntityIdNotMatchException.class
  })
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse entityIdExceptionHandler(Exception exception) {
    return new ErrorResponse(400, exception.getMessage());
  }
}
