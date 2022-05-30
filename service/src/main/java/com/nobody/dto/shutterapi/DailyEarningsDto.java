package com.nobody.dto.shutterapi;

import com.nobody.dto.BaseDto;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DailyEarningsDto extends BaseDto {
    private String date;
    private String earnings;
    private String downloads;
    private Photo photo;
    private Video video;
    private List<Category> categories;

}
