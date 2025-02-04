package com.spring.jwt.productMaster;

import com.spring.jwt.entity.ProductMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductMasterRepo extends JpaRepository<ProductMaster, Integer> {

    ProductMaster findByNameAndBrand(String name, String brand);
}
