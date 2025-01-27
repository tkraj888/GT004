package com.spring.jwt.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "product_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer masterId;

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
    private String type;

    @Column(nullable = false)
    private String mainType;

}
