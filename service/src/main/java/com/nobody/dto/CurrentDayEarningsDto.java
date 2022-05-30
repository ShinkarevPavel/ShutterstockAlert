package com.nobody.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CurrentDayEarningsDto extends BaseDto {
    private String date;
    private String downloads;
    private String earnings;
    private List<CategoryDto> categories;
}
