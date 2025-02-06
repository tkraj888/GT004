package com.spring.jwt.exception;

public class ProductMasterIdNotFound extends  RuntimeException{

    public ProductMasterIdNotFound(String message){
    super(message);
    }
}
