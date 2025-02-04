package com.spring.jwt.exception;

public class UserAndProductMasterAlreadyPresentException  extends RuntimeException{
    public UserAndProductMasterAlreadyPresentException(String message){
        super(message);
    }
}
