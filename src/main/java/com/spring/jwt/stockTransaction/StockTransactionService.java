package com.spring.jwt.stockTransaction;


import org.springframework.data.domain.Page;



import java.time.LocalDateTime;
import java.util.List;

public interface StockTransactionService  {
   List< StockTransactionDTO> getStockTransactionByUserID(Integer userId,Integer pageNo,Integer pageSize); // list


    List<StockTransactionDTO> getStockTansactionByUserProductID(Integer userProductId, Integer pageNo, Integer pageSize);

    StockTransactionDTO addStockTransaction(StockTransactionDTO transaction);

    List<StockTransactionDTO> getAllStockTransaction(Integer page, Integer size);

    StockTransactionDTO getByStockTransactionId(Integer transactionId);


    List<StockTransactionDTO> getByUserIdAndProductId(Integer userId, Integer userProductId, Integer pageNo, Integer pageSize);

    List<StockTransactionDTO> getByDateRange(LocalDateTime startDate, LocalDateTime endDate, Integer pageNo, Integer pageSize);

    List<StockTransactionDTO>  getByProductMasterId(Integer productMasterId,Integer pageNo, Integer pageSize );


    Page<StockTransactionDTO> getStockTransactionByDateRange(Integer userId, LocalDateTime startDate, LocalDateTime endDate, Integer pageNo, Integer pageSize);


    List<StockTransactionDTO> getStockTransactionByIdAndDate(Integer userId, LocalDateTime date, Integer pageNo, Integer pageSize); //only one


    List<StockTransactionDTO> getStockTransactionByBillNo(String billNo);// page not req

}
