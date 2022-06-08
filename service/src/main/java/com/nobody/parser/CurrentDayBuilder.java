package com.nobody.parser;

import com.nobody.dto.CategoryDto;
import com.nobody.dto.CurrentDayEarningsDto;
import com.nobody.dto.MonthSummaryEarningsDto;
import com.nobody.dto.shutterapi.DailyEarningsDto;
import com.nobody.dto.shutterapi.MonthlyEarningsDto;
import com.nobody.util.DateTimeBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CurrentDayBuilder {
    public CurrentDayEarningsDto buildCurrentDayEarningsDto(MonthlyEarningsDto monthlyEarningsDto) {
        List<DailyEarningsDto> days = monthlyEarningsDto.getDays();
        DailyEarningsDto current = getCurrent(days);
        return CurrentDayEarningsDto.builder()
                .date(current.getDate())
                .downloads(current.getDownloads())
                .earnings(current.getEarnings())
                .categories(current.getCategories().stream()
                        .map(d -> CategoryDto.builder()
                                .name(d.getName())
                                .earnings(d.getEarnings())
                                .downloads(d.getDownloads())
                                .build())
                        .collect(Collectors.toList())
                )
                .build();
    }

    public MonthSummaryEarningsDto getMonthEarnings(MonthlyEarningsDto monthlyEarningsDto) {
        return MonthSummaryEarningsDto.builder()
                .earnings(monthlyEarningsDto.getEarnings())
                .downloads(monthlyEarningsDto.getDownloads())
                .build();
    }

    private DailyEarningsDto getCurrent(List<DailyEarningsDto> days) {
        String currentDate = DateTimeBuilder.getCurrentDateYYYY_MM_ddFormat();
        return days.stream().filter(d -> d.getDate().equals(currentDate))
                .findFirst()
                .orElse(DailyEarningsDto.builder()
                        .date(currentDate)
                        .earnings("still empty :)")
                        .downloads("still empty :)")
                        .categories(new ArrayList<>()) // NPE workaround
                        .build());
    }
}
