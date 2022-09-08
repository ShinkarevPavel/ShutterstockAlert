package com.nobody.parser;

import com.nobody.dto.CurrentDayEarningsDto;
import com.nobody.dto.MonthSummaryEarningsDto;
import com.nobody.dto.shutterapi.Category;
import com.nobody.dto.shutterapi.DailyEarningsDto;
import com.nobody.dto.shutterapi.MonthlyEarningsDto;
import com.nobody.util.DateTimeBuilder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

@Component
public class CurrentDayBuilder {

  private final Logger logger = LogManager.getLogger();

  public CurrentDayEarningsDto buildCurrentDayEarningsDto(MonthlyEarningsDto monthlyEarningsDto) {
    List<DailyEarningsDto> days = monthlyEarningsDto.getDays();
    DailyEarningsDto current = getCurrent(days);
    Category subscription = current.getSubscription();
    Category onDemand = current.getOnDemand();
    Category enhanced = current.getEnhanced();
    Category singlesAndOther = current.getSinglesAndOther();

    LinkedHashMap<String, Category> categories = new LinkedHashMap<>();
    if (subscription != null) {
      categories.put("Subscription", subscription);
    }
    if (onDemand != null) {
      categories.put("On Demand", onDemand);
    }
    if (enhanced != null) {
      categories.put("Enhanced", enhanced);
    }
    if (singlesAndOther != null) {
      categories.put("Singles and Other", singlesAndOther);
    }
    return CurrentDayEarningsDto.builder()
        .date(current.getDate())
        .downloads(current.getDownloads())
        .earnings(current.getEarnings())
        .categories(categories)
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
    logger.log(Level.DEBUG, "Date that was get from backend - " + currentDate);
    logger.log(
        Level.DEBUG,
        "Pattern of date in monthly list is "
            + days.stream()
                .findFirst()
                .orElse(DailyEarningsDto.builder().earnings("Still Empty").build()));
    logger.log(
        Level.DEBUG, "Size of days in monthly list is " + (days != null ? days.size() : null));
    return days.stream()
        .filter(d -> d.getDate().equals(currentDate))
        .findFirst()
        .orElse(
            DailyEarningsDto.builder()
                .date(currentDate)
                .earnings("still empty:)")
                .downloads("still empty:)")
                .build());
  }
}
