package com.nobody.controller;

import com.nobody.dto.TelegramBotCredentials;
import com.nobody.sendler.MessageToTelegramSender;
import com.nobody.service.impl.TelegramServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class TelegramBotController {


    private TelegramServiceImpl telegramService;
    private MessageToTelegramSender toTelegramSender;

    @Autowired
    public TelegramBotController(TelegramServiceImpl telegramService, MessageToTelegramSender toTelegramSender) {
        this.telegramService = telegramService;
        this.toTelegramSender = toTelegramSender;
    }

    @PostMapping("/telegram")
    @ResponseStatus(HttpStatus.OK)
    public void setTelegramBotCredentials(@RequestBody TelegramBotCredentials botCredentials) {
        telegramService.setTelegramCredentials(botCredentials);
        toTelegramSender.setCredentials(botCredentials);
    }
}
