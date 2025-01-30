package com.spring.jwt.controller;

import com.spring.jwt.Interfaces.ProductMasterService;
import com.spring.jwt.dto.ProductMasterDTO;
import com.spring.jwt.dto.ResponceDto;
import com.spring.jwt.exception.DuplicateProductException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/productMaster")
public class ProductMasterController {

    @Autowired
    private ProductMasterService masterService;

    @PostMapping("/save")
    public ResponseEntity<ResponceDto> saveProductMaster(@RequestBody ProductMasterDTO masterDTO) {
        try {
            ProductMasterDTO dto = masterService.saveProductMaster(masterDTO);
            ResponceDto responseDto = new ResponceDto("ProductMaster saved successfully", dto);
            return ResponseEntity.status(HttpStatus.OK).body(responseDto);
        } catch (DuplicateProductException e) {
            ResponceDto responseDto = new ResponceDto("Failed to save ProductMaster", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
        } catch (Exception e) {
            ResponceDto responseDto = new ResponceDto("Failed to save ProductMaster", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }
}