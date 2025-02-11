package com.spring.jwt.productMaster;

import com.spring.jwt.dto.ResponseDto;
import com.spring.jwt.dto.ResponsingDTO;
import com.spring.jwt.exception.DuplicateProductException;
import com.spring.jwt.exception.ProductMasterIdNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestController
@RequestMapping("/productMaster")
public class  ProductMasterController {

    @Autowired
    private ProductMasterService productMasterService;


    @PostMapping("/save")
    public ResponseEntity<ResponseDto> saveProductMaster(@RequestBody ProductMasterDTO masterDTO) {
        try {
             productMasterService.saveProductMaster(masterDTO);
            ResponseDto responseDto = new ResponseDto("Successful", "ProductMaster saved successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (DuplicateProductException e) {
            ResponseDto responseDto = new ResponseDto("Unsuccessful", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
        } catch (Exception e) {
            ResponseDto responseDto = new ResponseDto("Unsuccessful", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }


    @GetMapping("/getByID")
    public ResponseEntity<ResponsingDTO> getProductMasterByID(@RequestParam Integer id) {
        try {
            ProductMasterDTO productMasterDTO = productMasterService.getProductByID(id);
            ResponsingDTO responsingDTO = new ResponsingDTO(id + " Found Successfully", productMasterDTO, false);
            return ResponseEntity.status(HttpStatus.FOUND).body(responsingDTO);
        } catch (ProductMasterIdNotFound e) {
            ResponsingDTO responsingDTO = new ResponsingDTO(e.getMessage(), null, true);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responsingDTO);
        } catch (MethodArgumentTypeMismatchException e) {
            ResponsingDTO responsingDTO = new ResponsingDTO(e.getMessage(), null, true);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responsingDTO);
        } catch (Exception e) {
            ResponsingDTO responsingDTO = new ResponsingDTO(e.getMessage() + e.getMessage(), null, true);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responsingDTO);
        }
    }


    @GetMapping("/all")
    public ResponseEntity<ResponsingDTO> getAllProductMasters(
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        try {
            List<ProductMasterDTO> productMasterList = productMasterService.getAllProducts(pageNo, pageSize);
            ResponsingDTO responsingDTO = new ResponsingDTO("All Products Retrieved Successfully", productMasterList, false);
            return ResponseEntity.status(HttpStatus.OK).body(responsingDTO);
        } catch (Exception e) {
            ResponsingDTO responsingDTO = new ResponsingDTO(e.getMessage(), null, true);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responsingDTO);
        }
    }




    @PatchMapping("/updateByID")
    public ResponseEntity<ResponsingDTO> updateProductMaster(@RequestParam Integer id, @RequestBody ProductMasterDTO masterDTO) {
        try {
            ProductMasterDTO updatedProduct = productMasterService.updateProductMasterByID(id, masterDTO);
            ResponsingDTO responsingDTO = new ResponsingDTO("ProductMaster updated successfully", updatedProduct, false);
            return ResponseEntity.status(HttpStatus.OK).body(responsingDTO);
        } catch (ProductMasterIdNotFound e) {
            ResponsingDTO responsingDTO = new ResponsingDTO(e.getMessage(), null, true);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responsingDTO);
        } catch (Exception e) {
            ResponsingDTO responsingDTO = new ResponsingDTO(e.getMessage(),null, true);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responsingDTO);
        }
    }


    @DeleteMapping("/deleteByID")
    public ResponseEntity<ResponseDto> deleteProductMasterByID(@RequestParam Integer id) {
        try {
            productMasterService.deleteProductByID(id);
            ResponseDto responseDto = new ResponseDto("Successful", "ProductMaster deleted successfully");
            return ResponseEntity.status(HttpStatus.OK).body(responseDto);
        } catch (ProductMasterIdNotFound e) {
            ResponseDto responseDto = new ResponseDto("Unsuccessful", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
        } catch (Exception e) {
            ResponseDto responseDto = new ResponseDto("Unsuccessful", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }

    @GetMapping("/searchByBrandAndName")
    public ResponseEntity<ResponseAllProductMaster> getProductsByBrandAndName(
            @RequestParam String brand, @RequestParam String name) {
        try {
            List<ProductMasterDTO> productDTOs = productMasterService.getProductByBrandAndName(brand, name);
            ResponseAllProductMaster response = new ResponseAllProductMaster("Products retrieved successfully", productDTOs, null);
            return ResponseEntity.ok(response);
        } catch (ProductMasterIdNotFound e) {
            ResponseAllProductMaster response = new ResponseAllProductMaster(e.getMessage(), null, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ResponseAllProductMaster response = new ResponseAllProductMaster("Error: " + e.getMessage(), null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


}
