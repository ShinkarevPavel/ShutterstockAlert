package com.nobody.config;

import com.nobody.exception.ShutterTelegramApiException;
import com.nobody.sendler.api.Bot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;

@SpringBootApplication
@ComponentScan("com.nobody")
@EnableScheduling
public class TestRunner extends SpringBootServletInitializer {

    private static ApplicationContext applicationContext;
    private final ApplicationContext context;

    @Autowired
    public TestRunner(ApplicationContext context) {
        this.context = context;
    }

    @PostConstruct
    private void init() {
        applicationContext = this.context;
    }

    public static void main(String[] args) {
        SpringApplication.run(TestRunner.class, args);
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot());
        } catch (TelegramApiException e) {
            throw new ShutterTelegramApiException("Error of telegram bot starting. " + e.getMessage(), e);
        }
    }

    private static Bot bot() {
        return applicationContext.getBean(Bot.class);
    }
}
