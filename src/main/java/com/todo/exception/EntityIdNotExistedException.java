package com.todo.exception;

public class EntityIdNotExistedException
     extends RuntimeException {

  public EntityIdNotExistedException() {
    super("Given entity's ID doesn't exist.");
  }
}
