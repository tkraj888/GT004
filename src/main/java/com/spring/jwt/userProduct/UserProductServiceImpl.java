package com.spring.jwt.userProduct;

import com.spring.jwt.entity.UserProduct;
import com.spring.jwt.exception.*;
import com.spring.jwt.productMaster.ProductMasterRepo;
import com.spring.jwt.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserProductServiceImpl implements UserProductService{

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductMasterRepo productMasterRepo;

    @Autowired
    private UserProductRepo userProductRepo;

    @Override
    public UserProductDTO saveUserProduct(UserProductDTO userProductDTO) {
        UserProduct userProduct=mapper.map(userProductDTO,UserProduct.class);
        userRepository.findById(userProduct.getUserId())
                .orElseThrow(()-> new UserIdNotFound("User not found with id:"+ userProduct.getUserId()));
        productMasterRepo.findById(userProduct.getProductMasterId())
                .orElseThrow(()->new ProductMasterIdNotFound("ProductMaster not found with id:"+userProduct.getProductMasterId()));
        UserProduct userProduct1=userProductRepo.findByUserIdAndProductMasterId(userProduct.getUserId(),userProduct.getProductMasterId());
        if(userProduct1!=null){
            throw new AlreadyIsPresent("UserID and MasterID already exist");
        }
        UserProduct savedUserProduct=userProductRepo.save(userProduct);
        return  mapper.map(savedUserProduct,UserProductDTO.class);
    }

    @Override
    public UserProductDTO getUserProductById(Integer id) {
        UserProduct userProduct=userProductRepo.findById(id).orElseThrow(()->new IdNotFoundException("Id:"+id+" not found"));
        return mapper.map(userProduct,UserProductDTO.class);

    }

    @Override
    public List<UserProductDTO> getAllUserProduct(Integer pageNo, Integer pageSize) {
        int defaultPageNo = (pageNo == null || pageNo < 1) ? 1 : pageNo;
        int defaultPageSize = (pageSize == null || pageSize < 1) ? 5 : pageSize;

        Pageable pageable = PageRequest.of(defaultPageNo - 1, defaultPageSize);
        Page<UserProduct> userProducts = userProductRepo.findAll(pageable);

        return userProducts.getContent().stream() // Extract the content as a list
                .map(userProduct -> mapper.map(userProduct, UserProductDTO.class))
                .collect(Collectors.toList()); // Convert to List<UserProductDTO>
    }





}
