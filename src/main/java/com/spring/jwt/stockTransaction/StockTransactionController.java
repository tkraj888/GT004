package com.spring.jwt.stockTransaction;

import com.spring.jwt.dto.ResponsingDTO;
import com.spring.jwt.exception.IdNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestController
public class StockTransactionController {

    @Autowired
    private StockTransactionService stockTransactionService;

    @GetMapping("/getStockTransactionByUserID")
    public ResponseEntity<ResponsingDTO> getStockTransactionByUserID(@RequestParam Integer userId ){
        try{
            StockTransactionDTO stockTransactionDTO = stockTransactionService.getStockTransactionByUserID(userId);
            ResponsingDTO responsingDTO = new ResponsingDTO(" Stock Transaction By using User ID ",stockTransactionDTO,false);
            return ResponseEntity.status(HttpStatus.FOUND).body(responsingDTO);
        }
        catch (IdNotFoundException e){
            ResponsingDTO responsingDTO = new ResponsingDTO(e.getMessage(),null,null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responsingDTO);
        }
        catch (MethodArgumentTypeMismatchException e) {
            ResponsingDTO responsingDTO = new ResponsingDTO(e.getMessage(), null, true);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responsingDTO);
        } catch (Exception e) {
            ResponsingDTO responsingDTO = new ResponsingDTO(e.getMessage() + e.getMessage(), null, true);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responsingDTO);
        }
    }

    @GetMapping("/getStockTransactionByUserProductID")
    public ResponseEntity<ResponsingDTO> getStockTansactionByUserProductID(@RequestParam Integer userProductId){
        try{
            List<StockTransactionDTO> stockTransactionDTOList = stockTransactionService.getStockTansactionByUserProductID(userProductId);
            ResponsingDTO responsingDTO = new ResponsingDTO("List of Stock Transaction By using User ProductID",stockTransactionDTOList,false);
            return ResponseEntity.status(HttpStatus.FOUND).body(responsingDTO);
        }catch (IdNotFoundException e){
            ResponsingDTO responsingDTO = new ResponsingDTO(e.getMessage(),null,null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responsingDTO);
        }
        catch (MethodArgumentTypeMismatchException e) {
            ResponsingDTO responsingDTO = new ResponsingDTO(e.getMessage(), null, true);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responsingDTO);
        } catch (Exception e) {
            ResponsingDTO responsingDTO = new ResponsingDTO(e.getMessage() + e.getMessage(), null, true);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responsingDTO);
        }
    }


}
