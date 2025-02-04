package com.spring.jwt.exception;

public class IdNotFoundException extends  RuntimeException{
    public IdNotFoundException(String message)
    {
        super(message);
    }

}
