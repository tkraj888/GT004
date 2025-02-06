package com.spring.jwt.stockTransaction;

import com.spring.jwt.dto.StockTransactionDTO;
import com.spring.jwt.entity.StockTransaction;
import com.spring.jwt.entity.UserProduct;
import com.spring.jwt.exception.IdNotFoundException;
import com.spring.jwt.exception.UserAndProductMasterAlreadyPresentException;
import com.spring.jwt.productMaster.ProductMasterRepo;
import com.spring.jwt.repository.UserRepository;
import com.spring.jwt.userProduct.UserProductDTO;
import com.spring.jwt.userProduct.UserProductRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private StockTransactionRepo repo;


    @Override
    public StockTransactionDTO addStockTransaction(StockTransactionDTO transaction) {

        StockTransaction transaction1=mapper.map(transaction, StockTransaction.class);
        userRepository.findById(transaction1.getUserId())
                .orElseThrow(()-> new IdNotFoundException("User not found with id:"+ transaction1.getUserId()));
        productMasterRepo.findById(transaction1.getProductMasterId())
                .orElseThrow(()->new IdNotFoundException("ProductMaster not found with id:"+transaction1.getProductMasterId()));
        Optional<UserProduct> userProduct=userProductRepo.findById(transaction1.getUserProduct01().getUserProductId());

        StockTransaction transaction2=repo.findByUserIdAndProductMasterIdAndUserProduct01_UserProductId(transaction1.getUserId(),transaction1.getProductMasterId(),transaction1.getUserProduct01().getUserProductId());

        if(transaction2!=null){
            throw new UserAndProductMasterAlreadyPresentException("UserID and MasterID already exist");
        }
        StockTransaction savedUserProduct=repo.save(transaction1);
        return  mapper.map(savedUserProduct, StockTransactionDTO.class);




    }
}
