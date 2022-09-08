package com.nobody.dto.shutterapi;

import com.nobody.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MonthlyEarningsDto extends BaseDto {
  private List<DailyEarningsDto> days;
  private String earnings;
  private String downloads;
  private MonthCategories categories;
}
