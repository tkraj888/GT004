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

import java.util.ArrayList;
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

//    @Override
//    public UserProductDTO getUserProductById(Integer id) {
//        UserProduct userProduct=userProductRepo.findById(id).orElseThrow(()->new IdNotFoundException("Id:"+id+" not found"));
//        return mapper.map(userProduct,UserProductDTO.class);
//
//    }

    @Override
    public UserProductDTO getUserProductById(Integer id) {
        UserProduct userProduct = userProductRepo.findById(id)
                .orElseThrow(() -> new UserProductIdNotFound("Id:" + id + " not found"));

        UserProductDTO dto = new UserProductDTO();
        dto.setUserProductId(userProduct.getUserProductId());
        dto.setProductMasterId(userProduct.getProductMasterId());
        dto.setName(userProduct.getName());
        dto.setBrand(userProduct.getBrand());
        dto.setStock90ml(userProduct.getStock90ml());
        dto.setStock180ml(userProduct.getStock180ml());
        dto.setStock360ml(userProduct.getStock360ml());
        dto.setStock760ml(userProduct.getStock760ml());
        dto.setStock1Liter(userProduct.getStock1Liter());
        dto.setStock2Liter(userProduct.getStock2Liter());
        dto.setUserId(userProduct.getUserId());
        dto.setDate(userProduct.getDate());
        dto.setType(userProduct.getType());
        dto.setMainType(userProduct.getMainType());

        return dto;
    }


//    @Override
//    public List<UserProductDTO> getAllUserProduct(Integer pageNo, Integer pageSize) {
//        int defaultPageNo = (pageNo == null || pageNo < 1) ? 1 : pageNo;
//        int defaultPageSize = (pageSize == null || pageSize < 1) ? 5 : pageSize;
//
//        Pageable pageable = PageRequest.of(defaultPageNo - 1, defaultPageSize);
//        Page<UserProduct> userProducts = userProductRepo.findAll(pageable);
//
//        return userProducts.getContent().stream() // Extract the content as a list
//                .map(userProduct -> mapper.map(userProduct, UserProductDTO.class))
//                .collect(Collectors.toList()); // Convert to List<UserProductDTO>
//    }

        @Override
        public List<UserProductDTO> getAllUserProduct(Integer pageNo, Integer pageSize) {

            int defaultPageNo = (pageNo == null || pageNo < 1) ? 1 : pageNo;
            int defaultPageSize = (pageSize == null || pageSize < 1) ? 5 : pageSize;

            Pageable pageable = PageRequest.of(defaultPageNo - 1, defaultPageSize);
            Page<UserProduct> userProducts = userProductRepo.findAll(pageable);

            List<UserProductDTO> userProductDTOList = new ArrayList<>();
            for (UserProduct userProduct : userProducts.getContent()) {

                UserProductDTO dto = new UserProductDTO();
                dto.setUserProductId(userProduct.getUserProductId());
                dto.setProductMasterId(userProduct.getProductMasterId());
                dto.setName(userProduct.getName());
                dto.setBrand(userProduct.getBrand());
                dto.setStock90ml(userProduct.getStock90ml());
                dto.setStock180ml(userProduct.getStock180ml());
                dto.setStock360ml(userProduct.getStock360ml());
                dto.setStock760ml(userProduct.getStock760ml());
                dto.setStock1Liter(userProduct.getStock1Liter());
                dto.setStock2Liter(userProduct.getStock2Liter());
                dto.setUserId(userProduct.getUserId());
                dto.setDate(userProduct.getDate());
                dto.setType(userProduct.getType());
                dto.setMainType(userProduct.getMainType());

                userProductDTOList.add(dto);
            }

        return userProductDTOList;
    }

    @Override
    public List<UserProductDTO> getUserProductByProductMasterID(Integer masterProductId, Integer pageNo, Integer pageSize) {
        int defaultPageNo = (pageNo == null || pageNo < 1) ? 1 : pageNo;
        int defaultPageSize = (pageSize == null || pageSize < 1) ? 5 : pageSize;
        Pageable pageable = PageRequest.of(defaultPageNo - 1, defaultPageSize);

        Page<UserProduct> userProducts = userProductRepo.getByProductMasterId(masterProductId, pageable);

        if (userProducts.isEmpty()) {
            throw new UserProductListNotFoundException("No products found for masterProductId: " + masterProductId);
        }

        return userProducts.getContent().stream()
                .map(userProduct -> new UserProductDTO(
                        userProduct.getUserProductId(),
                        userProduct.getProductMasterId(),
                        userProduct.getName(),
                        userProduct.getBrand(),
                        userProduct.getStock90ml(),
                        userProduct.getStock180ml(),
                        userProduct.getStock360ml(),
                        userProduct.getStock760ml(),
                        userProduct.getStock1Liter(),
                        userProduct.getStock2Liter(),
                        userProduct.getUserId(),
                        userProduct.getDate(),
                        userProduct.getType(),
                        userProduct.getMainType()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserProductDTO> getUserProductByUserID(Integer userID, Integer pageNo, Integer pageSize) {
        int defaultPageNo = (pageNo == null || pageNo < 1) ? 1 : pageNo;
        int defaultPageSize = (pageSize == null || pageSize < 1) ? 5 : pageSize;

        Pageable pageable = PageRequest.of(defaultPageNo - 1, defaultPageSize);
        Page<UserProduct> userProducts = userProductRepo.getByUserId(userID, pageable);

        if (userProducts.isEmpty()) {
            throw new UserProductIdNotFound("No products found for the given User ID");
        }

        return userProducts.getContent().stream().map(userProduct -> {
            UserProductDTO dto = new UserProductDTO();
            dto.setUserProductId(userProduct.getUserProductId());
            dto.setProductMasterId(userProduct.getProductMasterId());
            dto.setName(userProduct.getName());
            dto.setBrand(userProduct.getBrand());
            dto.setStock90ml(userProduct.getStock90ml());
            dto.setStock180ml(userProduct.getStock180ml());
            dto.setStock360ml(userProduct.getStock360ml());
            dto.setStock760ml(userProduct.getStock760ml());
            dto.setStock1Liter(userProduct.getStock1Liter());
            dto.setStock2Liter(userProduct.getStock2Liter());
            dto.setUserId(userProduct.getUserId());
            dto.setDate(userProduct.getDate());
            dto.setType(userProduct.getType());
            dto.setMainType(userProduct.getMainType());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<UserProductDTO> getUserProductByProductMasterIDAndUserID(Integer productMasterID, Integer userID, Integer pageNo, Integer pageSize) {
        int defaultPageNo = (pageNo == null || pageNo < 1) ? 1 : pageNo;
        int defaultPageSize = (pageSize == null || pageSize < 1) ? 5 : pageSize;

        Pageable pageable = PageRequest.of(defaultPageNo - 1, defaultPageSize);
        Page<UserProduct> userProducts = userProductRepo.findByProductMasterIdAndUserId(productMasterID, userID, pageable);

        if (userProducts.isEmpty()) {
            throw new UserProductIdNotFound("No products found for the given Product Master ID and User ID");
        }

        return userProducts.getContent().stream().map(userProduct -> {
            UserProductDTO dto = new UserProductDTO();
            dto.setUserProductId(userProduct.getUserProductId());
            dto.setProductMasterId(userProduct.getProductMasterId());
            dto.setName(userProduct.getName());
            dto.setBrand(userProduct.getBrand());
            dto.setStock90ml(userProduct.getStock90ml());
            dto.setStock180ml(userProduct.getStock180ml());
            dto.setStock360ml(userProduct.getStock360ml());
            dto.setStock760ml(userProduct.getStock760ml());
            dto.setStock1Liter(userProduct.getStock1Liter());
            dto.setStock2Liter(userProduct.getStock2Liter());
            dto.setUserId(userProduct.getUserId());
            dto.setDate(userProduct.getDate());
            dto.setType(userProduct.getType());
            dto.setMainType(userProduct.getMainType());
            return dto;
        }).collect(Collectors.toList());
    }


    @Override
    public List<UserProductDTO> getUserProductByUserIdAndBrand(Integer userId, String brand, Integer pageNo, Integer pageSize) {
        int defaultPageNo = (pageNo == null || pageNo < 1) ? 1 : pageNo;
        int defaultPageSize = (pageSize == null || pageSize < 1) ? 5 : pageSize;

        Pageable pageable = PageRequest.of(defaultPageNo - 1, defaultPageSize);
        Page<UserProduct> userProducts = userProductRepo.findByUserIdAndBrand(userId, brand, pageable);

        if (userProducts.isEmpty()) {
            throw new UserProductIdNotFound("No products found for the given User ID and Brand");
        }

        return userProducts.getContent().stream().map(userProduct -> {
            UserProductDTO dto = new UserProductDTO();
            dto.setUserProductId(userProduct.getUserProductId());
            dto.setProductMasterId(userProduct.getProductMasterId());
            dto.setName(userProduct.getName());
            dto.setBrand(userProduct.getBrand());
            dto.setStock90ml(userProduct.getStock90ml());
            dto.setStock180ml(userProduct.getStock180ml());
            dto.setStock360ml(userProduct.getStock360ml());
            dto.setStock760ml(userProduct.getStock760ml());
            dto.setStock1Liter(userProduct.getStock1Liter());
            dto.setStock2Liter(userProduct.getStock2Liter());
            dto.setUserId(userProduct.getUserId());
            dto.setDate(userProduct.getDate());
            dto.setType(userProduct.getType());
            dto.setMainType(userProduct.getMainType());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<UserProductDTO> getUserProductByUserIdMainTypeAndType(Integer userId, String mainType, String type, Integer pageNo, Integer pageSize) {
        int defaultPageNo = (pageNo == null || pageNo < 1) ? 1 : pageNo;
        int defaultPageSize = (pageSize == null || pageSize < 1) ? 5 : pageSize;

        Pageable pageable = PageRequest.of(defaultPageNo - 1, defaultPageSize);
        Page<UserProduct> userProducts = userProductRepo.findByUserIdAndMainTypeAndType(userId, mainType, type, pageable);

        if (userProducts.isEmpty()) {
            throw new UserProductIdNotFound("No products found for the given User ID, Main Type, and Type");
        }

        return userProducts.getContent().stream().map(userProduct -> {
            UserProductDTO dto = new UserProductDTO();
            dto.setUserProductId(userProduct.getUserProductId());
            dto.setProductMasterId(userProduct.getProductMasterId());
            dto.setName(userProduct.getName());
            dto.setBrand(userProduct.getBrand());
            dto.setStock90ml(userProduct.getStock90ml());
            dto.setStock180ml(userProduct.getStock180ml());
            dto.setStock360ml(userProduct.getStock360ml());
            dto.setStock760ml(userProduct.getStock760ml());
            dto.setStock1Liter(userProduct.getStock1Liter());
            dto.setStock2Liter(userProduct.getStock2Liter());
            dto.setUserId(userProduct.getUserId());
            dto.setDate(userProduct.getDate());
            dto.setType(userProduct.getType());
            dto.setMainType(userProduct.getMainType());
            return dto;
        }).collect(Collectors.toList());
    }


}





