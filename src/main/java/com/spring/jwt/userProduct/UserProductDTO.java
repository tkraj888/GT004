package com.spring.jwt.userProduct;

import com.spring.jwt.stockTransaction.StockTransactionDTO;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProductDTO {

    private Integer userProductId;
    private Integer productMasterId;
    private String name;
    private String brand;
    private Integer stock90ml;
    private Integer stock180ml;
    private Integer stock360ml;
    private Integer stock760ml;
    private Integer stock1Liter;
    private Integer stock2Liter;
    private Integer userId;
    private Date date;
    private String type;
    private String mainType;

}
