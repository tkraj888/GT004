package com.spring.jwt.productMaster;

import com.spring.jwt.entity.ProductMaster;
import com.spring.jwt.exception.DuplicateProductException;
import com.spring.jwt.exception.IdNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductMasterServiceImpl implements ProductMasterService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ProductMasterRepo productMasterRepo;

    @Override
    public ProductMasterDTO saveProductMaster( ProductMasterDTO productMasterDTO) {
        ProductMaster existingProduct = productMasterRepo.findByNameAndBrand(productMasterDTO.getName(), productMasterDTO.getBrand());
        if (existingProduct != null) {
            throw new DuplicateProductException("Product with the same name and brand already exists.");
        }
        ProductMaster productMaster = mapper.map(productMasterDTO, ProductMaster.class);
        ProductMaster savedProduct = productMasterRepo.save(productMaster);
        return mapper.map(savedProduct, ProductMasterDTO.class);
    }

    @Override
    public ProductMasterDTO getProductByID(Integer id) {
        ProductMaster productMaster = productMasterRepo.findById(id)
                .orElseThrow(() -> new IdNotFoundException("ProductMaster not found with id: " + id));
       return mapper.map(productMaster,ProductMasterDTO.class);
    }

    @Override
    public List<ProductMasterDTO> getAllProducts() {
        List<ProductMaster> products = productMasterRepo.findAll();
        return products.stream()
                .map(product -> mapper.map(product, ProductMasterDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProductByID(Integer id) {
        ProductMaster productMaster = productMasterRepo.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Product not found with id: " + id));
        productMasterRepo.delete(productMaster);
    }


        @Override
        public ProductMasterDTO updateProductMasterByID(Integer id, ProductMasterDTO productMasterDTO) {
            ProductMaster productMaster = productMasterRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("ProductMaster not found with id: " + id));

            if (productMasterDTO.getName() != null) {
                productMaster.setName(productMasterDTO.getName());
            }
            if (productMasterDTO.getBrand() != null) {
                productMaster.setBrand(productMasterDTO.getBrand());
            }
            if (productMasterDTO.getStock90ml() != null) {
                productMaster.setStock90ml(productMasterDTO.getStock90ml());
            }
            if (productMasterDTO.getStock180ml() != null) {
                productMaster.setStock180ml(productMasterDTO.getStock180ml());
            }
            if (productMasterDTO.getStock360ml() != null) {
                productMaster.setStock360ml(productMasterDTO.getStock360ml());
            }
            if (productMasterDTO.getStock760ml() != null) {
                productMaster.setStock760ml(productMasterDTO.getStock760ml());
            }
            if (productMasterDTO.getStock1Liter() != null) {
                productMaster.setStock1Liter(productMasterDTO.getStock1Liter());
            }
            if (productMasterDTO.getStock2Liter() != null) {
                productMaster.setStock2Liter(productMasterDTO.getStock2Liter());
            }
            if (productMasterDTO.getType() != null) {
                productMaster.setType(productMasterDTO.getType());
            }
            if (productMasterDTO.getMainType() != null) {
                productMaster.setMainType(productMasterDTO.getMainType());
            }

            ProductMaster updatedProductMaster = productMasterRepo.save(productMaster);
            return mapper.map(updatedProductMaster, ProductMasterDTO.class);
        }





    }

