package com.spring.jwt.stockTransaction;

import com.spring.jwt.dto.StockTransactionDTO;
import com.spring.jwt.entity.StockTransaction;
import com.spring.jwt.entity.UserProduct;
import com.spring.jwt.exception.IdNotFoundException;
import com.spring.jwt.exception.UserAndProductMasterAlreadyPresentException;
import com.spring.jwt.productMaster.ProductMasterRepo;
import com.spring.jwt.repository.UserRepository;
import com.spring.jwt.userProduct.UserProductRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StockTransactionServiceImpl implements StockTransactionService{

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductMasterRepo productMasterRepo;

    @Autowired
    private UserProductRepo userProductRepo;

    @Autowired
    private StockTransactionRepo stockTransactionRepo;


    @Override
    public StockTransactionDTO addStockTransaction(StockTransactionDTO transaction) {

        StockTransaction transaction1=mapper.map(transaction, StockTransaction.class);
        userRepository.findById(transaction1.getUserId())
                .orElseThrow(()-> new IdNotFoundException("User not found with id:"+ transaction1.getUserId()));
        productMasterRepo.findById(transaction1.getProductMasterId())
                .orElseThrow(()->new IdNotFoundException("ProductMaster not found with id:"+transaction1.getProductMasterId()));
        Optional<UserProduct> userProduct=userProductRepo.findById(transaction1.getUserProduct01().getUserProductId());

        StockTransaction transaction2=stockTransactionRepo.findByUserIdAndProductMasterIdAndUserProduct01_UserProductId(transaction1.getUserId(),transaction1.getProductMasterId(),transaction1.getUserProduct01().getUserProductId());

        if(transaction2!=null){
            throw new UserAndProductMasterAlreadyPresentException("UserID and MasterID already exist");
        }
        StockTransaction savedUserProduct=stockTransactionRepo.save(transaction1);
        return  mapper.map(savedUserProduct, StockTransactionDTO.class);




    }

    @Override
    public List<StockTransactionDTO> getAllStockTransaction(Integer page, Integer size) {

        int pageNumber = (page == null || page < 1) ? 1 : page;
        int pageSize = (size == null || size <= 0) ? 5 : size;

        Pageable pageable =  PageRequest.of(pageNumber -1 , pageSize);
        Page<StockTransaction> stockTransactionPage = stockTransactionRepo.findAll(pageable);

        List<StockTransactionDTO> stockTransactionDTOList = new ArrayList<>();
        for (StockTransaction stockTransaction : stockTransactionPage.getContent()) {
            StockTransactionDTO dto = mapper.map(stockTransaction, StockTransactionDTO.class);
            stockTransactionDTOList.add(dto);
        }
        return stockTransactionDTOList;
    }

    @Override
    public StockTransactionDTO getByIdStockTransaction(Integer transactionId) {

        StockTransaction stockTransaction= stockTransactionRepo.findById(transactionId).
                orElseThrow(()-> new IdNotFoundException("StockTransaction Id Not found"));
        return mapper.map(stockTransaction, StockTransactionDTO.class);
    }

}

