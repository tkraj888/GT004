package com.spring.jwt.exception;

public class StockTransactionAlreadyPresentException extends RuntimeException{
    public StockTransactionAlreadyPresentException(String message){
        super(message);
    }
}
