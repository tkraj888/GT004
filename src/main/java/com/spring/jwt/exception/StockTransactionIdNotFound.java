package com.spring.jwt.exception;

public class StockTransactionIdNotFound  extends RuntimeException{
    public StockTransactionIdNotFound(String s){
        super(s);
    }
}
