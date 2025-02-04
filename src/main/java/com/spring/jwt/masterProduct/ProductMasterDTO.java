package com.spring.jwt.masterProduct;

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
}
