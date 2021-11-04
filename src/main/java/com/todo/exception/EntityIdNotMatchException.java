package com.todo.exception;

public class EntityIdNotMatchException
     extends RuntimeException {

  public EntityIdNotMatchException() {
    super("Given entity's ID doesn't match the one in the path.");
  }
}
