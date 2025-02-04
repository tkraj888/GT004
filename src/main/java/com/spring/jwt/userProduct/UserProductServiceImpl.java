package com.spring.jwt.userProduct;

import com.spring.jwt.entity.UserProduct;
import com.spring.jwt.exception.IdNotFoundException;
import com.spring.jwt.exception.UserAlreadyExistException;
import com.spring.jwt.exception.UserAndProductMasterAlreadyPresentException;
import com.spring.jwt.productMaster.ProductMasterRepo;
import com.spring.jwt.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                .orElseThrow(()-> new IdNotFoundException("User not found with id:"+ userProduct.getUserId()));
        productMasterRepo.findById(userProduct.getProductMasterId())
                .orElseThrow(()->new IdNotFoundException("ProductMaster not found with id:"+userProduct.getProductMasterId()));
        UserProduct userProduct1=userProductRepo.findByUserIdAndProductMasterId(userProduct.getUserId(),userProduct.getProductMasterId());
        if(userProduct1!=null){
            throw new UserAndProductMasterAlreadyPresentException("UserID and MasterID already exist");
        }
        UserProduct savedUserProduct=userProductRepo.save(userProduct);
        return  mapper.map(savedUserProduct,UserProductDTO.class);
    }
}
