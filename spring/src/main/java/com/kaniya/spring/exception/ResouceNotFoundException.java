package com.kaniya.spring.exception;

public class ResouceNotFoundException extends RuntimeException {
    public  ResouceNotFoundException(String categoryNotFound) {
        super(categoryNotFound);
    }
}
