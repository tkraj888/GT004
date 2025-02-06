package com.spring.jwt.exception;

public class UserProductIdNotFound extends RuntimeException{
    public UserProductIdNotFound(String message){
        super(message);
    }
}
