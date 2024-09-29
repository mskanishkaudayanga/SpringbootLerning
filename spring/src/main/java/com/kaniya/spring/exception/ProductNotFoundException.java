package com.kaniya.spring.exception;

public class ProductNotFoundException extends  RuntimeException{
    public ProductNotFoundException(String massage) {
        super(massage);
    }
}
