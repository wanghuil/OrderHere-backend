package com.backend.OrderHere.exception;

public class DataIntegrityException extends RuntimeException{
  public DataIntegrityException(String message) {
    super(message);
  }
}
