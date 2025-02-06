package com.spring.jwt.stockTransaction;

import java.util.List;

public interface StockTransactionService  {
    StockTransactionDTO getStockTransactionByUserID(Integer userId);

    List<StockTransactionDTO> getStockTansactionByUserProductID(Integer userProductId, Integer pageNo, Integer pageSize);

    StockTransactionDTO addStockTransaction(StockTransactionDTO transaction);

    List<StockTransactionDTO> getAllStockTransaction(Integer page, Integer size);

    StockTransactionDTO getByIdStockTransaction(Integer transactionId);
}
