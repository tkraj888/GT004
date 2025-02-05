package com.spring.jwt.userProduct;

import com.spring.jwt.dto.ResponseDto;
import com.spring.jwt.dto.ResponsingDTO;
import com.spring.jwt.exception.IdNotFoundException;
import com.spring.jwt.exception.UserAlreadyExistException;
import com.spring.jwt.exception.UserAndProductMasterAlreadyPresentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userProduct")
public class UserProductController {

    @Autowired
    private UserProductService userProductService;


    @PostMapping("/save")
    public ResponseEntity<ResponseDto> saveUserProduct(@RequestBody UserProductDTO userProductDTO) {
        try {
            userProductService.saveUserProduct(userProductDTO);
            ResponseDto responseDto = new ResponseDto("Successful","UserProduct saved successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (IdNotFoundException e) {
            ResponseDto  responseDto = new ResponseDto("Unsuccessful", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
        } catch (UserAndProductMasterAlreadyPresentException e) {
            ResponseDto  responseDto = new ResponseDto("Unsuccessful", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);

        } catch (Exception e) {
            ResponseDto responseDto = new ResponseDto("Unsuccessful", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }

    }



}
