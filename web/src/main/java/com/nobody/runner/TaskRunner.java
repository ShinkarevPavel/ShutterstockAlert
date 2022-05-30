package com.nobody.runner;

import com.nobody.dto.shutterapi.MonthlyEarningsDto;
import com.nobody.parser.CommonDtoBuilder;
import com.nobody.https.ResponseHendler;
import com.nobody.parser.CurrentDayBuilder;
import com.nobody.sendler.MessageToTelegramSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskRunner implements Runnable {

    private MessageToTelegramSender sender;
    private ResponseHendler responseHendler;
    private CommonDtoBuilder commonDtoBuilder;
    private CurrentDayBuilder currentDayBuilder;

    @Autowired
    public TaskRunner(MessageToTelegramSender sender, ResponseHendler responseHendler, CommonDtoBuilder commonDtoBuilder, CurrentDayBuilder currentDayBuilder) {
        this.sender = sender;
        this.responseHendler = responseHendler;
        this.commonDtoBuilder = commonDtoBuilder;
        this.currentDayBuilder = currentDayBuilder;
    }

    @Override
    public void run() {
        String response = responseHendler.sendRequest();
        MonthlyEarningsDto monthlyEarningsDto = commonDtoBuilder.buildDto(response);
        sender.sendMessage(currentDayBuilder.buildCurrentDayEarningsDto(monthlyEarningsDto));
    }
}
