package com.nobody.controller;

import com.nobody.dto.TelegramBotCredentialsDto;
import com.nobody.sendler.MessageToTelegramSender;
import com.nobody.service.impl.TelegramServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/telegram")
public class TelegramBotController {

    private final Logger logger = LogManager.getLogger();
    private TelegramServiceImpl telegramService;
    private MessageToTelegramSender toTelegramSender;

    @Autowired
    public TelegramBotController(TelegramServiceImpl telegramService, MessageToTelegramSender toTelegramSender) {
        this.telegramService = telegramService;
        this.toTelegramSender = toTelegramSender;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TelegramBotCredentialsDto> getAll() {
        return telegramService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Validated(TelegramBotCredentialsDto.Create.class) @RequestBody TelegramBotCredentialsDto botCredentials) {
        telegramService.addEntity(botCredentials);
        logger.log(Level.INFO, "Telegram credentials were set");
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void remove(@RequestBody TelegramBotCredentialsDto telegramBotCredentialsDto) {
        telegramService.removeEntity(telegramBotCredentialsDto);
        logger.log(Level.INFO, "Telegram credentials were removed");
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public TelegramBotCredentialsDto update(@Validated(TelegramBotCredentialsDto.Update.class) @RequestBody TelegramBotCredentialsDto telegramBotCredentialsDto) {
        return telegramService.updateEntity(telegramBotCredentialsDto);

    }

    @PostMapping("/entity")
    public TelegramBotCredentialsDto getCredentials(@RequestBody TelegramBotCredentialsDto telegramBotCredentialsDto) {
        return telegramService.getEntity(telegramBotCredentialsDto);
    }

    @PostMapping("/access")
    @ResponseStatus(HttpStatus.OK)
    public void credentialsAccess(@RequestBody TelegramBotCredentialsDto telegramBotCredentialsDto) {
        telegramService.changeAvailability(telegramBotCredentialsDto);
        logger.log(Level.INFO, "Telegram credentials were updated");
    }
}
