package com.spring.jwt.userProduct;

import com.spring.jwt.entity.UserProduct;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserProductService {
    UserProductDTO saveUserProduct(UserProductDTO userProductDTO);

    UserProductDTO getUserProductById(Integer id);

    List<UserProductDTO> getAllUserProduct(Integer pageNo, Integer pageSize);

    List<UserProductDTO> getUserProductByProductMasterID(Integer masterProductID,Integer pageNo, Integer pageSize);

    List<UserProductDTO> getUserProductByUserID(Integer userID, Integer pageNo, Integer pageSize);

    List<UserProductDTO> getUserProductByProductMasterIDAndUserID(Integer productMasterID, Integer userID, Integer pageNo, Integer pageSize);

    List<UserProductDTO> getUserProductByUserIdAndBrand(Integer userId, String brand, Integer pageNo, Integer pageSize);

    List<UserProductDTO> getUserProductByUserIdMainTypeAndType(Integer userId, String mainType, String type, Integer pageNo, Integer pageSize);
}
