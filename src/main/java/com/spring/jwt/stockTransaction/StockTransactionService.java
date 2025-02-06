package com.spring.jwt.stockTransaction;

import com.spring.jwt.dto.StockTransactionDTO;

public interface StockTransactionService  {
    StockTransactionDTO addStockTransaction(StockTransactionDTO transaction);
}
