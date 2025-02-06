package com.spring.jwt.stockTransaction;

import com.spring.jwt.entity.StockTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockTransactionRepo extends JpaRepository<StockTransaction,Integer> {

    Page<StockTransaction> findByUserProduct01_UserProductId(Integer userProductId, Pageable pageable);
    StockTransaction findByUserIdAndProductMasterIdAndUserProduct01_UserProductId(
            Integer userId,
            Integer productMasterId,
            Integer userProductId
    );
}
