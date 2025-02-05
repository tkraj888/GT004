package com.spring.jwt.userProduct;

import com.spring.jwt.entity.UserProduct;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserProductService {
    UserProductDTO saveUserProduct(UserProductDTO userProductDTO);

    UserProductDTO getUserProductById(Integer id);

    List<UserProductDTO> getAllUserProduct(Integer pageNo, Integer pageSize);
}
