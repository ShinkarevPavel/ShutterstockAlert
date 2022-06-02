package com.nobody.sendler.api;

import com.nobody.exception.ShutterTelegramApiException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Bot extends TelegramLongPollingBot {

    private String token ="5390249782:AAHec3LDGlQ5EzHCxXrlZ25DR1fXTKe2fco";


    public static void main(String[] args) {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            throw new ShutterTelegramApiException("Error of telegram bot starting.", e);
        }
    }
    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public String getBotUsername() {
        return token;
    }

    @Override
    public String getBotToken() {
        return "nobodysShutterBot";
    }


}
