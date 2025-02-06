package com.spring.jwt.stockTransaction;

import com.spring.jwt.dto.ResponseDto;
import com.spring.jwt.dto.ResponsingDTO;
import com.spring.jwt.dto.StockTransactionDTO;
import com.spring.jwt.exception.IdNotFoundException;
import com.spring.jwt.exception.StockTransactionAlreadyPresentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/StockTransaction")
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

    @GetMapping("/GetAllStockTransaction")
    public ResponseEntity<ResponsingDTO> getAllStockTransaction(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {
        try {
            List<StockTransactionDTO> stockTransactionList = stockTransactionService.getAllStockTransaction(page, size);
            ResponsingDTO responsingDTO = new ResponsingDTO("Get All StockTransaction Successfully", stockTransactionList, false);
            return ResponseEntity.status(HttpStatus.OK).body(responsingDTO);
        } catch (Exception e) {
            ResponsingDTO responsingDTO = new ResponsingDTO(e.getMessage(), null, true);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responsingDTO);
        }
    }

    @GetMapping("/GetByIdStockTransaction")
    public ResponseEntity<ResponsingDTO> stockTransactionService(@RequestParam Integer transactionId){
        try{
            StockTransactionDTO stockTransactionDTO= stockTransactionService.getByIdStockTransaction(transactionId);
            ResponsingDTO responsingDTO= new ResponsingDTO(" Get By Id Data Successfully",stockTransactionDTO,false);
            return ResponseEntity.status(HttpStatus.OK).body(responsingDTO);
        }catch (Exception e){
            ResponsingDTO responsingDTO= new ResponsingDTO(e.getMessage(),null,true);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responsingDTO);
        }
    }
}









