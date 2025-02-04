package com.spring.jwt.productMaster;

import java.util.List;

public interface ProductMasterService {
    ProductMasterDTO saveProductMaster(ProductMasterDTO masterDTO) ;

    ProductMasterDTO getProductByID(Integer id);

    List<ProductMasterDTO> getAllProducts();

    ProductMasterDTO updateProductMasterByID(Integer id, ProductMasterDTO masterDTO);

    void deleteProductByID(Integer id);
}
