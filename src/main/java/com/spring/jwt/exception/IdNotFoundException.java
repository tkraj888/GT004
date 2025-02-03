package com.spring.jwt.exception;

public class IdNotFoundException extends  RuntimeException{
    public IdNotFoundException(String pageNotFound)
    {
        super("ID Not Found");
    }

}
