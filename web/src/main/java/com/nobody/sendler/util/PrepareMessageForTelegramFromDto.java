package com.nobody.sendler.util;

import com.nobody.dto.BaseDto;
import com.nobody.dto.CategoryEarningsType;
import com.nobody.dto.CurrentDayEarningsDto;

public class PrepareMessageForTelegramFromDto {

    public static String buildMessage(BaseDto dto) {
        CurrentDayEarningsDto dayEarningsDto = (CurrentDayEarningsDto) dto;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Date: ")
                .append(dayEarningsDto.getDate())
                .append("\n")
                .append("Downloads: ")
                .append(dayEarningsDto.getDownloads())
                .append("\n")
                .append("Earnings: ")
                .append(dayEarningsDto.getEarnings())
                .append("\n")
                .append("\n");
        dayEarningsDto.getCategories().forEach(c -> stringBuilder.append(CategoryEarningsType.getKeyForBotApi(c.getName()))
                .append(": ")
                .append(c.getDownloads())
                .append("--")
                .append(c.getEarnings())
                .append("$")
                .append("\n"));
        return stringBuilder.toString();
    }
}
