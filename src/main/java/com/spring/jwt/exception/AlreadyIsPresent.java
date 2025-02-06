package com.spring.jwt.exception;

public class AlreadyIsPresent extends RuntimeException{
    public AlreadyIsPresent(String message){
        super(message);
    }
}
