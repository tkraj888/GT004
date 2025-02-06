package com.spring.jwt.exception;

public class UserIdNotFound extends  RuntimeException{
    public UserIdNotFound(String message){
        super(message);
    }
}
