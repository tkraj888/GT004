package com.spring.jwt.service;

import com.spring.jwt.Interfaces.ProductMasterService;
import com.spring.jwt.dto.ProductMasterDTO;
import com.spring.jwt.entity.ProductMaster;
import com.spring.jwt.exception.DuplicateProductException;
import com.spring.jwt.repository.ProductMasterRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class ProductMasterServiceImpl implements ProductMasterService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ProductMasterRepo masterRepo;

    @Override
    public ProductMasterDTO saveProductMaster( ProductMasterDTO masterDTO) {
        ProductMaster existingProduct = masterRepo.findByNameAndBrand(masterDTO.getName(), masterDTO.getBrand());
        if (existingProduct != null) {
            throw new DuplicateProductException("Product with the same name and brand already exists.");
        }
        ProductMaster productMaster = mapper.map(masterDTO, ProductMaster.class);
        ProductMaster savedProduct = masterRepo.save(productMaster);
        return mapper.map(savedProduct, ProductMasterDTO.class);
    }
}

