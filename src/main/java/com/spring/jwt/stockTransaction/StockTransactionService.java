package com.spring.jwt.stockTransaction;

import com.spring.jwt.dto.StockTransactionDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StockTransactionService  {


    StockTransactionDTO addStockTransaction(StockTransactionDTO transaction);

    List<StockTransactionDTO> getAllStockTransaction(Integer page, Integer size);

    StockTransactionDTO getByIdStockTransaction(Integer transactionId);
}
