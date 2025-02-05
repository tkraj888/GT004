package com.spring.jwt.stockTransaction;

import com.spring.jwt.entity.StockTransaction;
import com.spring.jwt.exception.IdNotFoundException;
import com.spring.jwt.productMaster.ProductMasterDTO;
import com.spring.jwt.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockTransactionServiceImpl implements StockTransactionService{

    @Autowired
    private StockTransactionRepo stockTransactionRepo;

    @Autowired
    private ModelMapper mapper;

    private StockTransaction stockTransaction;


    @Override
    public StockTransactionDTO getStockTransactionByUserID(Integer userId) {
        StockTransaction stockTransaction = stockTransactionRepo.findById(userId)
                .orElseThrow(() -> new IdNotFoundException ("ProductMaster not found with id: " + userId));
        return mapper.map(stockTransaction, StockTransactionDTO.class);

    }

    @Override
    public List<StockTransactionDTO> getStockTansactionByUserProductID(Integer userProductId) {
        return List.of();
    }
}
