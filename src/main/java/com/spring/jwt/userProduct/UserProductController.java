package com.spring.jwt.userProduct;

import com.spring.jwt.dto.ResponsingDTO;
import com.spring.jwt.exception.IdNotFoundException;
import com.spring.jwt.exception.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userProduct")
public class UserProductController {

    @Autowired
    private UserProductService userProductService;


    @PostMapping("/save")
    public ResponseEntity<ResponsingDTO> saveUserProduct(@RequestBody UserProductDTO userProductDTO) {
        try {
            UserProductDTO userProductDTO1 = userProductService.saveUserProduct(userProductDTO);
            ResponsingDTO responsingDTO = new ResponsingDTO("UserProduct saved successfully", userProductDTO1, false);
            return ResponseEntity.status(HttpStatus.CREATED).body(responsingDTO);
        } catch (IdNotFoundException e) {
            ResponsingDTO responsingDTO = new ResponsingDTO("UserProduct saved unsuccessfully", e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responsingDTO);
        } catch (UserAlreadyExistException e) {
            ResponsingDTO responsingDTO = new ResponsingDTO("UserProduct saved unsuccessfully", e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responsingDTO);

        } catch (Exception e) {
            ResponsingDTO responsingDTO = new ResponsingDTO("UserProduct saved unsuccessfully", e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responsingDTO);
        }

    }

}
