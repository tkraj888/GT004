package com.spring.jwt.stockTransaction;


import com.spring.jwt.entity.StockTransaction;
import com.spring.jwt.entity.TransactionType;
import com.spring.jwt.entity.UserProduct;
import com.spring.jwt.exception.*;
import com.spring.jwt.productMaster.ProductMasterRepo;
import com.spring.jwt.repository.UserRepository;
import com.spring.jwt.userProduct.UserProductRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

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

    public StockTransactionDTO getStockTransactionByUserID(Integer userId) {
        StockTransaction stockTransaction = stockTransactionRepo.findById(userId)
                .orElseThrow(() -> new StockTransactionIdNotFound("ProductMaster not found with id: " + userId));

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
        dto.setUserProductId(stockTransaction.getUserProduct01().getUserProductId());

        return dto;
    }



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
                    dto.setUserProductId(transaction.getUserProduct01().getUserProductId());
                    return dto;
                })
                .collect(Collectors.toList());
    }



    @Override
    public StockTransactionDTO addStockTransaction(StockTransactionDTO transaction) {

        StockTransaction transaction1=mapper.map(transaction, StockTransaction.class);
        userRepository.findById(transaction1.getUserId())
                .orElseThrow(()-> new UserIdNotFound("User not found with id:"+ transaction1.getUserId()));
        productMasterRepo.findById(transaction1.getProductMasterId())
                .orElseThrow(()->new ProductMasterIdNotFound("ProductMaster not found with id:"+transaction1.getProductMasterId()));

        UserProduct userProduct = userProductRepo.findById(transaction.getUserProductId())
                .orElseThrow(() -> new UserProductIdNotFound("UserProduct not found with id: " + transaction.getUserProductId()));

        TransactionType transactionType=transaction1.getTransactionType();
        Integer stock180;
        Integer stock90;
        Integer stock360;
        Integer stock760;
        Integer stock1Litre;
        Integer stock2Liter;
        if(TransactionType.CREDIT.equals(transactionType)){
            stock180=userProduct.getStock180ml()+ transaction1.getQuantity180ml();
            stock90=userProduct.getStock90ml()+transaction1.getQuantity90ml();
            stock360=userProduct.getStock360ml()+transaction1.getQuantity360ml();
            stock760=userProduct.getStock760ml()+transaction1.getQuantity760ml();
            stock1Litre=userProduct.getStock1Liter()+ Integer.valueOf(transaction1.getQuantity1Liter());
            stock2Liter=userProduct.getStock2Liter()+ Integer.valueOf(transaction1.getQuantity2Liter());
            userProduct.setStock180ml(stock180);
            userProduct.setStock90ml(stock90);
            userProduct.setStock360ml(stock360);
            userProduct.setStock760ml(stock760);

            userProduct.setStock1Liter(stock1Litre);
            userProduct.setStock2Liter(stock2Liter);

        transaction1.setUserProduct01(userProduct);}
        else if(TransactionType.DEBIT.equals(transactionType)){
            stock180=userProduct.getStock180ml()- transaction1.getQuantity180ml();
            stock90=userProduct.getStock90ml()-transaction1.getQuantity90ml();
            stock360=userProduct.getStock360ml()-transaction1.getQuantity360ml();
            stock760= userProduct.getStock760ml()-transaction1.getQuantity760ml();
            stock1Litre=userProduct.getStock1Liter()- Integer.valueOf(transaction1.getQuantity1Liter());
            stock2Liter=userProduct.getStock2Liter()- Integer.valueOf(transaction1.getQuantity2Liter());
            userProduct.setStock180ml(stock180);
            userProduct.setStock90ml(stock90);
            userProduct.setStock360ml(stock360);
            userProduct.setStock760ml(stock760);
            userProduct.setStock1Liter(stock1Litre);
            transaction1.setUserProduct01(userProduct);
            userProduct.setStock2Liter(stock2Liter);

            }



        StockTransaction transaction2=stockTransactionRepo.findByUserIdAndProductMasterIdAndUserProduct01_UserProductId(transaction1.getUserId(),transaction1.getProductMasterId(),transaction1.getUserProduct01().getUserProductId());

        if(transaction2!=null){
            throw new AlreadyIsPresent("UserID,MasterID,UserProductID already exist");
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
            dto.setUserProductId(stockTransaction.getUserProduct01().getUserProductId() );


            dto.setUserProductId(stockTransaction.getUserProduct01().getUserProductId());
            stockTransactionDTOList.add(dto);
        }

        return stockTransactionDTOList;
    }



    @Override
    public StockTransactionDTO getByStockTransactionId(Integer transactionId) {

        StockTransaction stockTransaction = stockTransactionRepo.findById(transactionId)
                .orElseThrow(() -> new StockTransactionIdNotFound("StockTransaction Id Not found"));

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
        dto.setUserProductId(stockTransaction.getUserProduct01().getUserProductId() );


        return dto;
    }

    @Override
    public List<StockTransactionDTO> getByUserIdAndProductId(Integer userId, Integer userProductId, Integer pageNo, Integer size) {
        int pageNumber = (pageNo == null || pageNo < 1) ? 1 : pageNo;
        int pageSize = (size == null || size <= 0) ? 5 : size;

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<StockTransaction> stockTransactionPage=  stockTransactionRepo.findByUserIdAndUserProduct01_UserProductId(userId,userProductId,pageable);

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
                    dto.setUserProductId(transaction.getUserProduct01().getUserProductId() );

                    return dto;
                })
               .collect(Collectors.toList());
       //return List.of();
    }

    @Override
    public List<StockTransactionDTO> getByDateRange(LocalDateTime startDate, LocalDateTime endDate, Integer pageNo, Integer size) {

        int pageNumber = (pageNo == null || pageNo < 1) ? 1 : pageNo;
        int pageSize = (size == null || size <= 0) ? 5 : size;

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<StockTransaction> stockTransactionPage=  stockTransactionRepo.getByDateRange(startDate,endDate,pageable);

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
                    dto.setUserProductId(transaction.getUserProduct01().getUserProductId() );

                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<StockTransactionDTO> getByProductMasterId(Integer productMasterId, Integer pageNo, Integer pageSize) {

        int defaultPageNo = (pageNo == null || pageNo < 1) ? 1 : pageNo;
        int defaultPageSize = (pageSize == null || pageSize < 1) ? 5 : pageSize;

        Pageable pageable = PageRequest.of(defaultPageNo - 1, defaultPageSize);

        Page<StockTransaction> stockTransactionPage = stockTransactionRepo.findByProductMasterId(productMasterId, pageable);

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
                    dto.setUserProductId(transaction.getUserProduct01().getUserProductId());
                    return dto;
                })
                .collect(Collectors.toList());
    }
    @Override
    public Page<StockTransactionDTO> getStockTransactionByDateRange(Integer id, LocalDateTime startDate, LocalDateTime endDate, Integer pageNo, Integer pageSize) {
        int defaultPageNo = (pageNo == null || pageNo < 1) ? 0 : pageNo - 1;
        int defaultPageSize = (pageSize == null || pageSize < 1) ? 5 : pageSize;

        Pageable pageable = PageRequest.of(defaultPageNo, defaultPageSize);
        Page<StockTransaction> stockTransactions = stockTransactionRepo.findByUserIdAndDateRange(id, startDate, endDate, pageable);

        List<StockTransactionDTO> dtoList = new ArrayList<>();
        for (StockTransaction stockTransaction : stockTransactions.getContent()) {
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
            dto.setUserProductId(stockTransaction.getUserProduct01().getUserProductId());
            dtoList.add(dto);
        }

        return new PageImpl<>(dtoList, pageable, stockTransactions.getTotalElements());
    }

    @Override
    public List<StockTransactionDTO> getStockTransactionByIdAndDate(Integer id, LocalDateTime date, Integer pageNo, Integer pageSize) {
        int defaultPageNo = (pageNo == null || pageNo < 1) ? 0 : pageNo - 1;
        int defaultPageSize = (pageSize == null || pageSize < 1) ? 5 : pageSize;

        Pageable pageable = PageRequest.of(defaultPageNo, defaultPageSize);


        Page<StockTransaction> stockTransactions = stockTransactionRepo.getStockTransactionByIdAndDate(id, date, pageable);

        List<StockTransactionDTO> stockTransactionDTOs = new ArrayList<>();
        for (StockTransaction stockTransaction : stockTransactions.getContent()) {
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
            dto.setUserProductId(stockTransaction.getUserProduct01().getUserProductId());
            stockTransactionDTOs.add(dto);
        }

        return stockTransactionDTOs;
    }

    @Override
    public List<StockTransactionDTO> getStockTransactionByBillNo(String billNo, Integer pageNo, Integer pageSize) {

        int defaultPageNo =(pageNo==null || pageSize < 1)? 0 : pageNo-1;
        int defaultPageSize=(pageSize == null || pageSize < 1) ? 5 : pageSize;

        Pageable pageable= PageRequest.of(defaultPageNo,defaultPageSize);

        Page<StockTransaction> stockTransactions= stockTransactionRepo.getStockTransactionByBillNo(billNo,pageable);

        List<StockTransactionDTO> stockTransactionDTOs = new ArrayList<>();
        for (StockTransaction stockTransaction : stockTransactions.getContent()) {
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
            dto.setUserProductId(stockTransaction.getUserProduct01().getUserProductId());
            stockTransactionDTOs.add(dto);
        }
        return stockTransactionDTOs;
    }


}

