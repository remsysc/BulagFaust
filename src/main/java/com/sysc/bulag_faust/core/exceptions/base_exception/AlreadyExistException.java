package com.sysc.bulag_faust.core.exceptions.base_exception;

public class AlreadyExistException extends RuntimeException {

  public AlreadyExistException(String resourceName, Object identifier) {
    super(resourceName + " already exists with identifier: " + identifier);
  }

}
