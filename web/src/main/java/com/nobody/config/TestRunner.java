package com.nobody.config;

import com.nobody.exception.ShutterTelegramApiException;
import com.nobody.sendler.api.Bot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
@ComponentScan("com.nobody")
@EnableScheduling
public class TestRunner extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(TestRunner.class, args);
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot());
        } catch (TelegramApiException e) {
            throw new ShutterTelegramApiException("Error of telegram bot starting. " + e.getMessage(), e);
        }
    }

    @Bean
    public static Bot bot() {
        return new Bot();
    }
}
