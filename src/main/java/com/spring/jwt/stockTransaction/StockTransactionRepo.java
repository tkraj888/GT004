package com.spring.jwt.stockTransaction;

import com.spring.jwt.entity.StockTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockTransactionRepo extends JpaRepository<StockTransaction,Integer> {
    StockTransaction findByUserIdAndProductMasterIdAndUserProduct01_UserProductId(
            Integer userId,
            Integer productMasterId,
            Integer userProductId
    );
}
