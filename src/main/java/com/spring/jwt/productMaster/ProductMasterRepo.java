package com.spring.jwt.productMaster;

import com.spring.jwt.entity.ProductMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMasterRepo extends JpaRepository<ProductMaster, Integer> {

    ProductMaster findByNameAndBrand(String name, String brand);

    List<ProductMaster> findByBrandAndName(String brand, String name);

    List<ProductMaster> findByBrandContainingIgnoreCaseAndNameContainingIgnoreCase(String brand, String name);
}
