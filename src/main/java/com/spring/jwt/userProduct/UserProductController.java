package com.spring.jwt.userProduct;

import com.spring.jwt.dto.ResponseDto;
import com.spring.jwt.dto.ResponsingDTO;
import com.spring.jwt.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestController
@RequestMapping("/userProduct")
public class UserProductController {

    @Autowired
    private UserProductService userProductService;


    @PostMapping("/save")
    public ResponseEntity<ResponseDto> saveUserProduct(@RequestBody UserProductDTO userProductDTO) {
        try {
            userProductService.saveUserProduct(userProductDTO);
            ResponseDto responseDto = new ResponseDto("Successful", "UserProduct saved successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        }
        catch (ProductMasterIdNotFound e) {
            ResponseDto responseDto = new ResponseDto("Unsuccessful", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
        }
        catch (AlreadyIsPresent e) {
            ResponseDto responseDto = new ResponseDto("Unsuccessful", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);

        } catch (Exception e) {
            ResponseDto responseDto = new ResponseDto("Unsuccessful", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }

    }

    @GetMapping("/getByID")
    public ResponseEntity<ResponsingDTO> getUserProductById(@RequestParam Integer id) {
        try {
            UserProductDTO userProductDTO = userProductService.getUserProductById(id);
            ResponsingDTO responsingDTO = new ResponsingDTO(id + " Found Successfully", userProductDTO, false);
            return ResponseEntity.status(HttpStatus.FOUND).body(responsingDTO);
        } catch (UserProductIdNotFound e) {
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
    public ResponseEntity<ResponsingDTO> getAllUserProduct(
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        try {
            List<UserProductDTO> userProductList = userProductService.getAllUserProduct(pageNo, pageSize);
            ResponsingDTO responsingDTO = new ResponsingDTO("All User Products Retrieved Successfully", userProductList, false);
            return ResponseEntity.status(HttpStatus.OK).body(responsingDTO);
        } catch (Exception e) {
            ResponsingDTO responsingDTO = new ResponsingDTO(e.getMessage(), null, true);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responsingDTO);
        }
    }




}
