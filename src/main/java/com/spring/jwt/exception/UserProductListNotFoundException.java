package com.spring.jwt.exception;

public class UserProductListNotFoundException extends RuntimeException {
    public UserProductListNotFoundException(String message) {
        super(message);
    }
}

