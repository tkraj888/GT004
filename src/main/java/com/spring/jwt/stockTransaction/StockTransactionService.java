package com.spring.jwt.stockTransaction;

import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface StockTransactionService  {
    StockTransactionDTO getStockTransactionByUserID(Integer userId);

    List<StockTransactionDTO> getStockTansactionByUserProductID(Integer userProductId, Integer pageNo, Integer pageSize);

    StockTransactionDTO addStockTransaction(StockTransactionDTO transaction);

    List<StockTransactionDTO> getAllStockTransaction(Integer page, Integer size);

    StockTransactionDTO getByIdStockTransaction(Integer transactionId);


    Page<StockTransactionDTO> getStockTransactionByDateRange(Integer id, LocalDateTime startDate, LocalDateTime endDate, Integer pageNo, Integer pageSize);


    List<StockTransactionDTO> getStockTransactionByIdAndDate(Integer id, LocalDateTime date, Integer pageNo, Integer pageSize);


    List<StockTransactionDTO> getStockTransactionByBillNo(String billNo, Integer pageNo, Integer pageSize);
}
