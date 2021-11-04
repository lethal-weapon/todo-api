package com.todo.exception;

public class EntityNotFoundException
     extends RuntimeException {

  public EntityNotFoundException() {
    super("The entity doesn't exist");
  }
}
