package com.spring.jwt.Interfaces;

import com.spring.jwt.dto.ProductMasterDTO;

import java.sql.SQLException;

public interface ProductMasterService {
    ProductMasterDTO saveProductMaster(ProductMasterDTO masterDTO) ;
}
