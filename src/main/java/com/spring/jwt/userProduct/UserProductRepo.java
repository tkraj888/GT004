package com.spring.jwt.userProduct;

import com.spring.jwt.entity.UserProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProductRepo extends JpaRepository<UserProduct,Integer> {

    UserProduct findByUserIdAndProductMasterId(Integer userId ,Integer  productMasterId);


    Page<UserProduct> getByProductMasterId(Integer masterProductId, Pageable pageable);

    Page<UserProduct> getByUserId(Integer userID, Pageable pageable);

    //Page<UserProduct> findByProductMasterIdAndUserId(Integer productMasterId, Integer userId, Pageable pageable);

    Page<UserProduct> findByProductMasterIdAndUserId(Integer productMasterID, Integer userID, Pageable pageable);

    Page<UserProduct> findByUserIdAndBrand(Integer userId, String brand, Pageable pageable);

    Page<UserProduct> findByUserIdAndMainTypeAndType(Integer userId, String mainType, String type, Pageable pageable);
}
