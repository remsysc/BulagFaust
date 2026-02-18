package com.sysc.bulag_faust.core.exceptions.base_exception;

public class NotFoundException extends RuntimeException {
  public NotFoundException(String resourceName, Object identifier) {
    super(resourceName + " not found with identifier: " + identifier);
  }
}
