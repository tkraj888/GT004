package com.spring.jwt.stockTransaction;

import java.util.List;

public interface StockTransactionService  {
    StockTransactionDTO getStockTransactionByUserID(Integer userId);

    List<StockTransactionDTO> getStockTansactionByUserProductID(Integer userProductId);
}
