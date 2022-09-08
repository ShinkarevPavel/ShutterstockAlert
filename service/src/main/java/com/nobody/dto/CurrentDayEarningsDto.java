package com.nobody.dto;

import com.nobody.dto.shutterapi.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CurrentDayEarningsDto extends BaseDto {
  private String date;
  private String downloads;
  private String earnings;
  private Map<String, Category> categories;
}
