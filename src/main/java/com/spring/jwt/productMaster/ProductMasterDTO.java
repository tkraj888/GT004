package com.spring.jwt.productMaster;

import com.spring.jwt.entity.ProductMaster;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductMasterDTO {

    private Integer productMasterId;
    private String name;
    private String brand;
    private Integer stock90ml;
    private Integer stock180ml;
    private Integer stock360ml;
    private Integer stock760ml;
    private Integer stock1Liter;
    private Integer stock2Liter;
    private String type;
    private String mainType;

    public ProductMasterDTO(ProductMaster productMaster) {
        this.productMasterId = productMaster.getProductMasterId();
        this.name = productMaster.getName();
        this.brand = productMaster.getBrand();
        this.stock90ml = productMaster.getStock90ml();
        this.stock180ml = productMaster.getStock180ml();
        this.stock360ml = productMaster.getStock360ml();
        this.stock760ml = productMaster.getStock760ml();
        this.stock1Liter = productMaster.getStock1Liter();
        this.stock2Liter = productMaster.getStock2Liter();
        this.type = productMaster.getType();
        this.mainType = productMaster.getMainType();
    }

    public ProductMasterDTO() {
    }
}
