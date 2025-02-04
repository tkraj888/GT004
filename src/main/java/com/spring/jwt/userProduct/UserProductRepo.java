package com.spring.jwt.userProduct;

import com.spring.jwt.entity.UserProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProductRepo extends JpaRepository<UserProduct,Integer> {

    UserProduct findByUserIdAndProductMasterId(Integer userId ,Integer  productMasterId);


}
