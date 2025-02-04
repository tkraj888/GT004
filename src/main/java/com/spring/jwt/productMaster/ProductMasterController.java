package com.spring.jwt.productMaster;

import com.spring.jwt.dto.ResponsingDTO;
import com.spring.jwt.exception.DuplicateProductException;
import com.spring.jwt.exception.IdNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestController
@RequestMapping("/productMaster")
public class ProductMasterController {

    @Autowired
    private ProductMasterService productMasterService;


    @PostMapping("/save")
    public ResponseEntity<ResponsingDTO> saveProductMaster(@RequestBody ProductMasterDTO masterDTO) {
        try {
            ProductMasterDTO productMaster = productMasterService.saveProductMaster(masterDTO);
            ResponsingDTO responsingDTO = new ResponsingDTO("ProductMaster saved successfully", productMaster, false);
            return ResponseEntity.status(HttpStatus.CREATED).body(responsingDTO);
        } catch (DuplicateProductException e) {
            ResponsingDTO responsingDTO = new ResponsingDTO("Failed to save ProductMaster", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responsingDTO);
        } catch (Exception e) {
            ResponsingDTO responsingDTO = new ResponsingDTO("Failed to save ProductMaster", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responsingDTO);
        }
    }


    @GetMapping("/getByID")
    public ResponseEntity<ResponsingDTO> getProductMasterByID(@RequestParam Integer id) {
        try {
            ProductMasterDTO productMasterDTO = productMasterService.getProductByID(id);
            ResponsingDTO responsingDTO = new ResponsingDTO(id + " Found Successfully", productMasterDTO, false);
            return ResponseEntity.status(HttpStatus.OK).body(responsingDTO);
        } catch (IdNotFoundException e) {
            ResponsingDTO responsingDTO = new ResponsingDTO(id + " Not Found", null, true);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responsingDTO);
        } catch (MethodArgumentTypeMismatchException e) {
            ResponsingDTO responsingDTO = new ResponsingDTO("Invalid UUID format: " + id, null, true);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responsingDTO);
        } catch (Exception e) {
            ResponsingDTO responsingDTO = new ResponsingDTO("Unexpected error: " + e.getMessage(), null, true);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responsingDTO);
        }
    }


    @GetMapping("/all")
    public ResponseEntity<ResponsingDTO> getAllProductMasters() {
        try {
            List<ProductMasterDTO> productMasterList = productMasterService.getAllProducts();
            ResponsingDTO responsingDTO = new ResponsingDTO("All Products Retrieved Successfully", productMasterList, false);
            return ResponseEntity.status(HttpStatus.OK).body(responsingDTO);
        } catch (Exception e) {
            ResponsingDTO responsingDTO = new ResponsingDTO("Failed to retrieve products", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responsingDTO);
        }
    }


    @PatchMapping("/updateByID")
    public ResponseEntity<ResponsingDTO> updateProductMaster(@RequestParam Integer id, @RequestBody ProductMasterDTO masterDTO) {
        try {
            ProductMasterDTO updatedProduct = productMasterService.updateProductMasterByID(id, masterDTO);
            ResponsingDTO responsingDTO = new ResponsingDTO("ProductMaster updated successfully", updatedProduct, false);
            return ResponseEntity.status(HttpStatus.OK).body(responsingDTO);
        } catch (IdNotFoundException e) {
            ResponsingDTO responsingDTO = new ResponsingDTO("ProductMaster not found", null, true);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responsingDTO);
        } catch (Exception e) {
            ResponsingDTO responsingDTO = new ResponsingDTO("Failed to update ProductMaster", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responsingDTO);
        }
    }


    @DeleteMapping("/deleteByID")
    public ResponseEntity<ResponsingDTO> deleteProductMasterByID(@RequestParam Integer id) {
        try {
            productMasterService.deleteProductByID(id);
            ResponsingDTO responsingDTO = new ResponsingDTO("ProductMaster deleted successfully", null, false);
            return ResponseEntity.status(HttpStatus.OK).body(responsingDTO);
        } catch (IdNotFoundException e) {
            ResponsingDTO responsingDTO = new ResponsingDTO("ProductMaster not found", null, true);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responsingDTO);
        } catch (Exception e) {
            ResponsingDTO responsingDTO = new ResponsingDTO("Failed to delete ProductMaster", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responsingDTO);
        }
    }
}
