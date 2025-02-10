package com.spring.jwt.dto;

import lombok.Data;

@Data
public class ResponsingDTO {
    public String message;
    public Object object;
    public Boolean hasError;

    public ResponsingDTO(String message, Object object, Boolean hasError) {
        this.message = message;
        this.object = object;
        this.hasError = hasError;
    }

}
