package com.spring.jwt.stockTransaction;


import com.spring.jwt.entity.ProductMaster;
import com.spring.jwt.entity.StockTransaction;
import com.spring.jwt.entity.TransactionType;
import com.spring.jwt.exception.IdNotFoundException;
import com.spring.jwt.entity.UserProduct;
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

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class StockTransactionServiceImpl implements StockTransactionService{

    @Autowired
    private StockTransactionRepo stockTransactionRepo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
   private UserRepository userRepository;

    @Autowired
    private ProductMasterRepo productMasterRepo;

    @Autowired
    private UserProductRepo  userProductRepo;

    private StockTransaction stockTransaction;


//    @Override
//    public StockTransactionDTO getStockTransactionByUserID(Integer userId) {
//        StockTransaction stockTransaction = stockTransactionRepo.findById(userId)
//                .orElseThrow(() -> new IdNotFoundException ("ProductMaster not found with id: " + userId));
//        return mapper.map(stockTransaction, StockTransactionDTO.class);
//
//    }

    public StockTransactionDTO getStockTransactionByUserID(Integer userId) {
        StockTransaction stockTransaction = stockTransactionRepo.findById(userId)
                .orElseThrow(() -> new IdNotFoundException("ProductMaster not found with id: " + userId));

        // Manually mapping entity to DTO
        StockTransactionDTO dto = new StockTransactionDTO();
        dto.setTransactionId(stockTransaction.getTransactionId());
        dto.setProductMasterId(stockTransaction.getProductMasterId());
        dto.setName(stockTransaction.getName());
        dto.setBrand(stockTransaction.getBrand());
        dto.setTransactionType(String.valueOf(stockTransaction.getTransactionType()));
        dto.setQuantity90ml(stockTransaction.getQuantity90ml());
        dto.setQuantity180ml(stockTransaction.getQuantity180ml());
        dto.setQuantity360ml(stockTransaction.getQuantity360ml());
        dto.setQuantity760ml(stockTransaction.getQuantity760ml());
        dto.setQuantity1Liter(stockTransaction.getQuantity1Liter());
        dto.setQuantity2Liter(stockTransaction.getQuantity2Liter());
        dto.setTransactionDate(stockTransaction.getTransactionDate());
        dto.setRemarks(stockTransaction.getRemarks());
        dto.setBillNo(stockTransaction.getBillNo());
        dto.setUserId(stockTransaction.getUserId());
        dto.setType(stockTransaction.getType());
        dto.setMainType(stockTransaction.getMainType());

        return dto;
    }


//    @Override
//    public List<StockTransactionDTO> getStockTansactionByUserProductID(Integer userProductId, Integer pageNo, Integer pageSize) {
//
//        int defaultPageNo = (pageNo == null || pageNo < 1) ? 1 : pageNo;
//        int defaultPageSize = (pageSize == null || pageSize < 1) ? 5 : pageSize;
//
//        Pageable pageable = PageRequest.of(defaultPageNo - 1, defaultPageSize);
//
//        Page<StockTransaction> stockTransactionPage = stockTransactionRepo.findByUserProduct01_UserProductId(userProductId, pageable);
//
//        return stockTransactionPage.getContent().stream()
//                .map(transaction -> mapper.map(transaction, StockTransactionDTO.class))
//                .collect(Collectors.toList());
//    }

    @Override
    public List<StockTransactionDTO> getStockTansactionByUserProductID(Integer userProductId, Integer pageNo, Integer pageSize) {

        int defaultPageNo = (pageNo == null || pageNo < 1) ? 1 : pageNo;
        int defaultPageSize = (pageSize == null || pageSize < 1) ? 5 : pageSize;

        Pageable pageable = PageRequest.of(defaultPageNo - 1, defaultPageSize);

        Page<StockTransaction> stockTransactionPage = stockTransactionRepo.findByUserProduct01_UserProductId(userProductId, pageable);

        return stockTransactionPage.getContent().stream()
                .map(transaction -> {
                    StockTransactionDTO dto = new StockTransactionDTO();
                    dto.setTransactionId(transaction.getTransactionId());
                    dto.setProductMasterId(transaction.getProductMasterId());
                    dto.setName(transaction.getName());
                    dto.setBrand(transaction.getBrand());
                    dto.setTransactionType(String.valueOf(transaction.getTransactionType()));
                    dto.setQuantity90ml(transaction.getQuantity90ml());
                    dto.setQuantity180ml(transaction.getQuantity180ml());
                    dto.setQuantity360ml(transaction.getQuantity360ml());
                    dto.setQuantity760ml(transaction.getQuantity760ml());
                    dto.setQuantity1Liter(transaction.getQuantity1Liter());
                    dto.setQuantity2Liter(transaction.getQuantity2Liter());
                    dto.setTransactionDate(transaction.getTransactionDate());
                    dto.setRemarks(transaction.getRemarks());
                    dto.setBillNo(transaction.getBillNo());
                    dto.setUserId(transaction.getUserId());
                    dto.setType(transaction.getType());
                    dto.setMainType(transaction.getMainType());
                    return dto;
                })
                .collect(Collectors.toList());
    }




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

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<StockTransaction> stockTransactionPage = stockTransactionRepo.findAll(pageable);

        List<StockTransactionDTO> stockTransactionDTOList = new ArrayList<>();
        for (StockTransaction stockTransaction : stockTransactionPage.getContent()) {
            StockTransactionDTO dto = new StockTransactionDTO();
            dto.setTransactionId(stockTransaction.getTransactionId());
            dto.setProductMasterId(stockTransaction.getProductMasterId());
            dto.setName(stockTransaction.getName());
            dto.setBrand(stockTransaction.getBrand());
            dto.setTransactionType(String.valueOf(stockTransaction.getTransactionType()));
            dto.setQuantity90ml(stockTransaction.getQuantity90ml());
            dto.setQuantity180ml(stockTransaction.getQuantity180ml());
            dto.setQuantity360ml(stockTransaction.getQuantity360ml());
            dto.setQuantity760ml(stockTransaction.getQuantity760ml());
            dto.setQuantity1Liter(stockTransaction.getQuantity1Liter());
            dto.setQuantity2Liter(stockTransaction.getQuantity2Liter());
            dto.setTransactionDate(stockTransaction.getTransactionDate());
            dto.setRemarks(stockTransaction.getRemarks());
            dto.setBillNo(stockTransaction.getBillNo());
            dto.setUserId(stockTransaction.getUserId());
            dto.setType(stockTransaction.getType());
            dto.setMainType(stockTransaction.getMainType());

            stockTransactionDTOList.add(dto);
        }

        return stockTransactionDTOList;
    }


//    @Override
//    public StockTransactionDTO getByIdStockTransaction(Integer transactionId) {
//
//        StockTransaction stockTransaction= stockTransactionRepo.findById(transactionId).
//                orElseThrow(()-> new IdNotFoundException("StockTransaction Id Not found"));
//        return mapper.map(stockTransaction, StockTransactionDTO.class);
//    }


    @Override
    public StockTransactionDTO getByIdStockTransaction(Integer transactionId) {

        StockTransaction stockTransaction = stockTransactionRepo.findById(transactionId)
                .orElseThrow(() -> new IdNotFoundException("StockTransaction Id Not found"));

        StockTransactionDTO dto = new StockTransactionDTO();
        dto.setTransactionId(stockTransaction.getTransactionId());
        dto.setProductMasterId(stockTransaction.getProductMasterId());
        dto.setName(stockTransaction.getName());
        dto.setBrand(stockTransaction.getBrand());
        dto.setTransactionType(String.valueOf(stockTransaction.getTransactionType()));
        dto.setQuantity90ml(stockTransaction.getQuantity90ml());
        dto.setQuantity180ml(stockTransaction.getQuantity180ml());
        dto.setQuantity360ml(stockTransaction.getQuantity360ml());
        dto.setQuantity760ml(stockTransaction.getQuantity760ml());
        dto.setQuantity1Liter(stockTransaction.getQuantity1Liter());
        dto.setQuantity2Liter(stockTransaction.getQuantity2Liter());
        dto.setTransactionDate(stockTransaction.getTransactionDate());
        dto.setRemarks(stockTransaction.getRemarks());
        dto.setBillNo(stockTransaction.getBillNo());
        dto.setUserId(stockTransaction.getUserId());
        dto.setType(stockTransaction.getType());
        dto.setMainType(stockTransaction.getMainType());

        return dto;
    }

}

