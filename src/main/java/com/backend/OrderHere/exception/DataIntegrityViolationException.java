package com.backend.OrderHere.exception;

public class DataIntegrityViolationException extends RuntimeException {

    public DataIntegrityViolationException(String message) {
        super(message);
    }
}
