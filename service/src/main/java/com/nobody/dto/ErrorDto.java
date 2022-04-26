package com.nobody.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class ErrorDto extends BaseDto{
    private String message;
    private String exceptionMessage;
    private String errorCode;
}
