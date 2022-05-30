package com.nobody.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nobody.dto.BaseDto;
import com.nobody.dto.shutterapi.MonthlyEarningsDto;
import com.nobody.exception.ShutterStockParseException;
import org.springframework.stereotype.Component;

@Component
public class CommonDtoBuilder extends BaseDto {

    public MonthlyEarningsDto buildDto(String response) {
        MonthlyEarningsDto monthlyEarningsDto;
        try {
           monthlyEarningsDto = new ObjectMapper().readValue(response, MonthlyEarningsDto.class);
        } catch (JsonProcessingException e) {
            throw new ShutterStockParseException("Error of parse shutterstocks common dto." + e.getMessage());
        }
        return monthlyEarningsDto;
    }
}
