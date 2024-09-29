package com.kaniya.spring.exception;

public class AlredyExistsException extends RuntimeException {
    public AlredyExistsException(String message) {
        super(message);
    }
}
