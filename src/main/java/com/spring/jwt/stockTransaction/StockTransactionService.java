package com.spring.jwt.stockTransaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface StockTransactionService  {
    StockTransactionDTO getStockTransactionByUserID(Integer userId);

    List<StockTransactionDTO> getStockTansactionByUserProductID(Integer userProductId, Integer pageNo, Integer pageSize);

    StockTransactionDTO addStockTransaction(StockTransactionDTO transaction);

    List<StockTransactionDTO> getAllStockTransaction(Integer page, Integer size);

    StockTransactionDTO getByIdStockTransaction(Integer transactionId);

    List<StockTransactionDTO> getByUserIdAndProductId(Integer userId, Integer userProductId, Integer pageNo, Integer pageSize);

    List<StockTransactionDTO> getByDateRange(LocalDateTime startDate, LocalDateTime endDate, Integer pageNo, Integer pageSize);

    List<StockTransactionDTO>  getByProductMasterId(Integer productMasterId,Integer pageNo, Integer pageSize );
}
