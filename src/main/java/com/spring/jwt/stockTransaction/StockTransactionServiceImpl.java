package com.spring.jwt.stockTransaction;

import com.spring.jwt.entity.StockTransaction;
import com.spring.jwt.exception.IdNotFoundException;
import com.spring.jwt.productMaster.ProductMasterDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<StockTransactionDTO> getStockTansactionByUserProductID(Integer userProductId, Integer pageNo, Integer pageSize) {

        int defaultPageNo = (pageNo == null || pageNo < 1) ? 1 : pageNo;
        int defaultPageSize = (pageSize == null || pageSize < 1) ? 5 : pageSize;

        Pageable pageable = PageRequest.of(defaultPageNo - 1, defaultPageSize);

        Page<StockTransaction> stockTransactionPage = stockTransactionRepo.findByUserProduct01_UserProductId(userProductId, pageable);

        return stockTransactionPage.getContent().stream()
                .map(transaction -> mapper.map(transaction, StockTransactionDTO.class))
                .collect(Collectors.toList());
    }


}
