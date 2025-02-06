package com.spring.jwt.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "stock_transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Column(nullable = false)
    private Integer productMasterId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType; // CREDIT or DEBIT

    @Column(nullable = false)
    private Integer quantity90ml;

    @Column(nullable = false)
    private Integer quantity180ml;

    @Column(nullable = false)
    private Integer quantity360ml;

    @Column(nullable = false)
    private Integer quantity760ml;

    @Column(nullable = false)
    private String quantity1Liter;

    @Column(nullable = false)
    private String quantity2Liter;

    @Column(nullable = false)
    private LocalDateTime transactionDate;

    @Column(length = 255)
    private String remarks;

    @Column(length = 255)
    private String billNo;

    @Column(length = 255)
    private Integer userId;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String mainType;


    @ManyToOne
    @JoinColumn(name = "userProductId")
    @JsonIgnore
    private UserProduct userProduct01;

}
