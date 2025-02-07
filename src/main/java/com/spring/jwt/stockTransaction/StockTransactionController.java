package com.spring.jwt.stockTransaction;


import com.spring.jwt.dto.ResponsingDTO;
import com.spring.jwt.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import com.spring.jwt.dto.ResponseDto;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/StockTransaction")
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
        catch (StockTransactionIdNotFound e){
            ResponsingDTO responsingDTO = new ResponsingDTO(e.getMessage(),null,true);
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
    public ResponseEntity<ResponsingDTO> getStockTansactionByUserProductID(@RequestParam Integer userProductId,
                                                                           @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                                           @RequestParam(value = "pageSize", required = false) Integer pageSize ){
        try{
            List<StockTransactionDTO> stockTransactionDTOList = stockTransactionService.getStockTansactionByUserProductID(userProductId,pageNo,pageSize);
            ResponsingDTO responsingDTO = new ResponsingDTO("List of Stock Transaction By using User ProductID",stockTransactionDTOList,false);
            return ResponseEntity.status(HttpStatus.FOUND).body(responsingDTO);
        }catch (StockTransactionIdNotFound e){
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



    @PostMapping("/addStock")
    public ResponseEntity<ResponseDto> addStockTransaction(@RequestBody StockTransactionDTO transaction) {
        try {
            stockTransactionService.addStockTransaction(transaction);
            ResponseDto responseDto = new ResponseDto("Successful", "Stock Transaction saved successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (UserIdNotFound e) {
            ResponseDto responseDto = new ResponseDto("Unsuccessful", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
        }
        catch (ProductMasterIdNotFound e) {
            ResponseDto responseDto = new ResponseDto("Unsuccessful", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
        }
        catch (UserProductIdNotFound e) {
            ResponseDto responseDto = new ResponseDto("Unsuccessful", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
        }
        catch (AlreadyIsPresent e) {
            ResponseDto responseDto = new ResponseDto("Unsuccessful", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(responseDto);
        } catch (Exception e) {
            ResponseDto responseDto = new ResponseDto("Unsuccessful", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }

    @GetMapping("/ByUserId&UserProductId")
    public ResponseEntity<ResponsingDTO> getByUserIdAndProductId(@RequestParam Integer userId,
                                                                 @RequestParam Integer userProductId,
                                                                 @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                                 @RequestParam(value = "pageSize", required = false) Integer pageSize)
    {
        try{
            List<StockTransactionDTO>  stockTransactionDTOList=stockTransactionService.getByUserIdAndProductId(userId,userProductId,pageNo,pageSize);
            ResponsingDTO  responsingDTO=new ResponsingDTO("Get All StockTransaction",stockTransactionDTOList,false);
            return ResponseEntity.status(HttpStatus.FOUND).body(responsingDTO);

        }
        catch (UserIdNotFound e) {
            ResponsingDTO responsingDTO = new ResponsingDTO( e.getMessage(),null,true);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responsingDTO);
        }
        catch (UserProductIdNotFound e) {
            ResponsingDTO responsingDTO = new ResponsingDTO( e.getMessage(),null,true);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responsingDTO);
        }
        catch (Exception e) {
        ResponsingDTO responsingDTO = new ResponsingDTO( e.getMessage(),null,true);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responsingDTO);
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
        }

        catch (Exception e) {
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



    @GetMapping("/ByDateRange")
    public ResponseEntity<ResponsingDTO> getByDateRange( @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                                        @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                        @RequestParam(value = "pageSize", required = false) Integer pageSize){
        try{

//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            LocalDateTime start = LocalDateTime.parse(startDate + " 00:00:00", formatter);
//            LocalDateTime end = LocalDateTime.parse(endDate + " 23:59:59", formatter);


            List<StockTransactionDTO> stockTransactionDTOList=stockTransactionService.getByDateRange(startDate,endDate,pageNo,pageSize);
            ResponsingDTO responsingDTO= new ResponsingDTO(" Get By Id Data Successfully",stockTransactionDTOList,false);
            return ResponseEntity.status(HttpStatus.OK).body(responsingDTO);
        }
        catch(Exception e){
            ResponsingDTO responsingDTO= new ResponsingDTO(e.getMessage(),null,true);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responsingDTO);
        }
    }


    @GetMapping("/GetByProductMasterId")
    public ResponseEntity<ResponsingDTO> stockTransactionByProductMasterId(@RequestParam Integer productMasterId,
                                                                             @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                                           @RequestParam(value = "pageSize", required = false) Integer pageSize){
        try{
           List< StockTransactionDTO> stockTransactionDTO= stockTransactionService.getByProductMasterId(productMasterId,pageNo,pageSize);
            ResponsingDTO responsingDTO= new ResponsingDTO(" Get By Id Data Successfully",stockTransactionDTO,false);
            return ResponseEntity.status(HttpStatus.OK).body(responsingDTO);
        }catch (Exception e){
            ResponsingDTO responsingDTO= new ResponsingDTO(e.getMessage(),null,true);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responsingDTO);
        }
    }

}









