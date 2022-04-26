package com.nobody.parser;

import com.nobody.dto.DailyEarningsDto;
import com.nobody.exception.ShutterStockParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


@Component
public class PageParser {

    public DailyEarningsDto getData(String htmlPage) throws ShutterStockParseException{
        Document document = Jsoup.parse(htmlPage);
        Elements calendarDays = document.select("tbody > tr");
        if (calendarDays.size() == 0) {
            throw new ShutterStockParseException("PageParser throws exception. Error of earnings page parsing. Probably response return wrong page");
        }
        Element currentDateState = null;
        String date = "";
        for (Element element : calendarDays) {
            date = element.getElementsByTag("a").text();
            if (getCurrentDate().equals(date)) {
                currentDateState = element;
                break;
            }
        }
        if (Objects.isNull(currentDateState)) {
            throw new ShutterStockParseException("PageParser throws exception. Error of date parsing. Probably response page was changed");
        }
        return buildDtoFromElement(currentDateState, date);
    }

    private DailyEarningsDto buildDtoFromElement(Element currentElement, String date) {
        String totalDownloads = currentElement.getElementsByClass("text-right bg-lgt-blue").text();
        String totalEarnings = currentElement.getElementsByClass("text-right table-bdr-rgt bg-lgt-blue").text();
        String subscription = currentElement.getElementsByClass("text-right ").eq(0).text();
        String onDemand = currentElement.getElementsByClass("text-right ").eq(1).text();
        String enhanced = currentElement.getElementsByClass("text-right ").eq(2).text();;
        String singles = currentElement.getElementsByClass("text-right  table-bdr-rgt ").eq(0).text();

        return DailyEarningsDto.builder()
                .date(date)
                .totalDownloads(totalDownloads)
                .totalEarnings(totalEarnings)
                .subscriptions(subscription)
                .onDemand(onDemand)
                .enhanced(enhanced)
                .singles(singles)
                .build();
    }

    private String getCurrentDate() {
        String pattern = "M/d/yy";
        ZonedDateTime mst = ZonedDateTime.now(ZoneId.of("America/Indiana/Indianapolis"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return mst.format(formatter);
    }
}
