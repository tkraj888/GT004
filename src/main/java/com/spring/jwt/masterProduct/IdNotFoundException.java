package com.spring.jwt.masterProduct;

public class IdNotFoundException extends  RuntimeException{
    public IdNotFoundException(String pageNotFound)
    {
        super("ID Not Found");
    }

}
