package com.spring.jwt.productMaster;

import lombok.Data;

import java.util.List;

@Data
public class ResponseAllProductMaster {

    private String message;
    private List<ProductMasterDTO> productMasterDTOS;
    private String exception;

    public ResponseAllProductMaster(String message, List<ProductMasterDTO> productMasterDTOS, String exception) {
        this.message = message;
        this.productMasterDTOS = productMasterDTOS;
        this.exception = exception;
    }
}
