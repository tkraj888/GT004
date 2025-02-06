package com.spring.jwt.stockTransaction;

import com.spring.jwt.dto.ResponseDto;
import com.spring.jwt.dto.StockTransactionDTO;
import com.spring.jwt.entity.StockTransaction;
import com.spring.jwt.exception.IdNotFoundException;
import com.spring.jwt.exception.StockTransactionAlreadyPresentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stockTransaction")
public class StockTransactionController {

    @Autowired
    private StockTransactionService stockTransactionService;

    @PostMapping("/addStock")
    public ResponseEntity<ResponseDto> addStockTransaction(@RequestBody StockTransactionDTO transaction) {
        try {
            stockTransactionService.addStockTransaction(transaction);
            ResponseDto responseDto = new ResponseDto("Successful", "ProductMaster saved successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (IdNotFoundException e) {
            ResponseDto responseDto = new ResponseDto("Unsuccessful", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
        }
        catch(StockTransactionAlreadyPresentException e){
            ResponseDto responseDto=new ResponseDto("Unsuccessful",e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
        }
        catch (Exception e) {
            ResponseDto responseDto = new ResponseDto("Unsuccessful", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
        }
    }
}