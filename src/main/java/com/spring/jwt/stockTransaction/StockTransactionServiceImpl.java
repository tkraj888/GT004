package com.spring.jwt.stockTransaction;

import com.spring.jwt.dto.StockTransactionDTO;
import com.spring.jwt.entity.StockTransaction;
import com.spring.jwt.exception.IdNotFoundException;
import com.spring.jwt.productMaster.ProductMasterDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockTransactionServiceImpl implements StockTransactionService{

    @Autowired
   private StockTransactionRepo stockTransactionRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<StockTransactionDTO> getAllStockTransaction(Integer page, Integer size) {

        int pageNumber = (page == null || page < 1) ? 1 : page;
        int pageSize = (size == null || size <= 0) ? 5 : size;

        Pageable pageable =  PageRequest.of(pageNumber -1 , pageSize);
        Page<StockTransaction> stockTransactionPage = stockTransactionRepo.findAll(pageable);

        List<StockTransactionDTO> stockTransactionDTOList = new ArrayList<>();
        for (StockTransaction stockTransaction : stockTransactionPage.getContent()) {
            StockTransactionDTO dto = modelMapper.map(stockTransaction, StockTransactionDTO.class);
            stockTransactionDTOList.add(dto);
        }
        return stockTransactionDTOList;
    }

    @Override
    public StockTransactionDTO getByIdStockTransaction(Integer transactionId) {

        StockTransaction stockTransaction= stockTransactionRepo.findById(transactionId).
                orElseThrow(()-> new IdNotFoundException("StockTransaction Id Not found"));
        return modelMapper.map(stockTransaction, StockTransactionDTO.class);
    }

}
