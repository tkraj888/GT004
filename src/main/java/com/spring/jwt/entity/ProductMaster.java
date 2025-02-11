package com.spring.jwt.entity;
import com.spring.jwt.productMaster.ProductMasterDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "product_master")
@Data
@AllArgsConstructor
@Builder
public class ProductMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String type;

    @Column(nullable = false)
    private String mainType;


    public ProductMaster() {
    }

    public ProductMaster(ProductMasterDTO productMasterDTO) {
        this.productMasterId = productMasterDTO.getProductMasterId();
        this.name = productMasterDTO.getName();
        this.brand = productMasterDTO.getBrand();
        this.stock90ml = productMasterDTO.getStock90ml();
        this.stock180ml = productMasterDTO.getStock180ml();
        this.stock360ml = productMasterDTO.getStock360ml();
        this.stock760ml = productMasterDTO.getStock760ml();
        this.stock1Liter = productMasterDTO.getStock1Liter();
        this.stock2Liter = productMasterDTO.getStock2Liter();
        this.type = productMasterDTO.getType();
        this.mainType = productMasterDTO.getMainType();
    }
}
