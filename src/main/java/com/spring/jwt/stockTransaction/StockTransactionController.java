package com.spring.jwt.stockTransaction;

import com.spring.jwt.dto.ResponsingDTO;
import com.spring.jwt.dto.StockTransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/StockTransaction")
public class StockTransactionController {


    @Autowired
    private StockTransactionService stockTransactionService;

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







