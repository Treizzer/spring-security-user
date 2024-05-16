package com.spring.security.services.models.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDTO {

    private Integer numOfErrors;
    private String message;

}
