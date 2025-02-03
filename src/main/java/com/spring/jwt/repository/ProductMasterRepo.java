package com.spring.jwt.repository;

import com.spring.jwt.entity.ProductMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductMasterRepo extends JpaRepository<ProductMaster, Integer> {

    ProductMaster findByNameAndBrand(String name, String brand);
}
