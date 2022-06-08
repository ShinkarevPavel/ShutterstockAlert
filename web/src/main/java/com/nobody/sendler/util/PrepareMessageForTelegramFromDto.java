package com.nobody.sendler.util;

import com.nobody.dto.*;

public class PrepareMessageForTelegramFromDto {

    private static final String SUCCESS_HEADER = "✅ Shutterstock Bot(v.1.3)\n";
    private static final String FAILED_HEADER = "⚠️Shutterstock Bot(v.1.3)\n";

    public static String buildMessage(BaseDto dto) {
        String text = "";
        if (dto instanceof ErrorDto) {
            text = FAILED_HEADER;
            text += ((ErrorDto) dto).getMessage() + "\n" + ((ErrorDto) dto).getExceptionMessage(); //TODO
        } else if (dto instanceof CurrentDayEarningsDto){
            text = SUCCESS_HEADER;
            text += buildCurrentDayText(dto);
        } else if (dto instanceof MonthSummaryEarningsDto) {
            text = SUCCESS_HEADER;
            text += buildMonthSummaryText(dto);
        }
        return text;
    }


    private static String buildCurrentDayText(BaseDto dto) {
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

    private static String buildMonthSummaryText(BaseDto dto) {
        MonthSummaryEarningsDto summaryEarningsDto = (MonthSummaryEarningsDto) dto;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Downloads: ")
                .append(summaryEarningsDto.getDownloads())
                .append("Earnings: ")
                .append(summaryEarningsDto.getEarnings());
        return stringBuilder.toString();
    }
}
