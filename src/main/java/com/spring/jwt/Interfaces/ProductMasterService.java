package com.spring.jwt.Interfaces;

import com.spring.jwt.dto.ProductMasterDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface ProductMasterService {
    ProductMasterDTO saveProductMaster(ProductMasterDTO masterDTO) ;

    ProductMasterDTO getProductByID(Integer id);

    List<ProductMasterDTO> getAllProducts();

    ProductMasterDTO updateProductMasterByID(Integer id, ProductMasterDTO masterDTO);

    void deleteProductByID(Integer id);
}
