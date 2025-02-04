package com.spring.jwt.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user_product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userProductId;

    @Column(nullable = false)
    private Integer productMasterId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private Integer stock90ml;

    @Column(nullable = false)
    private Integer stock180ml;

    @Column(nullable = false)
    private Integer stock360ml;

    @Column(nullable = false)
    private Integer stock760ml;

    @Column(nullable = false)
    private Integer stock1Liter;

    @Column(nullable = false)
    private Integer stock2Liter;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String mainType;

    @OneToMany(mappedBy = "userProduct01")
    private Set<StockTransaction> stockTransactions = new LinkedHashSet<>();
}
