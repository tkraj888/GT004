package com.spring.jwt.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock_transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Column(nullable = false)
    private Integer masterId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType; // CREDIT or DEBIT

    @Column(nullable = false)
    private Integer quantity90ml; // Quantity in milliliters for 90ml

    @Column(nullable = false)
    private Integer quantity180ml; // Quantity in milliliters for 180ml

    @Column(nullable = false)
    private Integer quantity360ml; // Quantity in milliliters for 360ml

    @Column(nullable = false)
    private Integer quantity760ml; // Quantity in milliliters for 760ml

    @Column(nullable = false)
    private Integer quantity1Liter; // Quantity in milliliters for 1L

    @Column(nullable = false)
    private Integer quantity2Liter; // Quantity in milliliters for 2L

    @Column(nullable = false)
    private LocalDateTime transactionDate;

    @Column(length = 255)
    private String remarks;

    @Column(length = 255)
    private String billNo;

    @ManyToOne
    @JoinColumn(name = "UserId")
    private User user;

    @ManyToMany
    @JoinColumn(name = "userProductId")
    private UserProduct userProduct;

    @Column(length = 255)
    private Integer userId;
}
