package com.spring.jwt.stockTransaction;

import com.spring.jwt.entity.StockTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;

@Repository
public interface StockTransactionRepo extends JpaRepository<StockTransaction,Integer> {

    Page<StockTransaction> findByUserProduct01_UserProductId(Integer userProductId, Pageable pageable);
    StockTransaction findByUserIdAndProductMasterIdAndUserProduct01_UserProductId(
            Integer userId,
            Integer productMasterId,
            Integer userProductId
    );


    Page<StockTransaction> findByUserIdAndUserProduct01_UserProductId(Integer userId,Integer userProductId,Pageable pageable);

    @Query("SELECT s FROM StockTransaction s WHERE s.transactionDate BETWEEN :startDate AND :endDate")
    Page<StockTransaction> getByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, Pageable pageable);

    Page<StockTransaction> findByProductMasterId(Integer productMasterId, Pageable pageable);

    @Query("SELECT s FROM StockTransaction s WHERE s.userId = :userId AND s.transactionDate BETWEEN :startDate AND :endDate")
    Page<StockTransaction> findByUserIdAndDateRange(
            @Param("userId") Integer userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );

    @Query("SELECT s FROM StockTransaction s WHERE s.userId = :userId AND s.transactionDate = :date")
    Page<StockTransaction> getStockTransactionByIdAndDate(@Param("userId") Integer id, @Param("date") LocalDateTime date, Pageable pageable);


    @Query("SELECT s FROM StockTransaction s WHERE s.billNo = :billNo")
    Page<StockTransaction> getStockTransactionByBillNo(@Param("billNo") String billNo, Pageable pageable);

}
