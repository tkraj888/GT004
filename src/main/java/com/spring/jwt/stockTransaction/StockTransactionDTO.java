package com.spring.jwt.stockTransaction;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockTransactionDTO {

    private Long transactionId;
    private Integer productMasterId;
    private String name;
    private String brand;
    private String transactionType; // CREDIT or DEBIT
    private Integer quantity90ml;
    private Integer quantity180ml;
    private Integer quantity360ml;
    private Integer quantity760ml;
    private String quantity1Liter;
    private String quantity2Liter;
    private LocalDateTime transactionDate;
    private String remarks;
    private String billNo;
    private Integer userId;
    private String type;
    private String mainType;
    private Integer userProductId; // Reference to UserProduct entity
}
