package com.nobody.sendler.api;

import com.nobody.runner.ApplicationScheduler;
import com.nobody.runner.TaskRunner;
import com.nobody.saver.TelegramCredentialsSaver;
import com.nobody.util.CronPatternChanger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class Bot extends TelegramLongPollingBot {

    private TaskRunner taskRunner;
    private CronPatternChanger cronPatternChanger;
    private ApplicationScheduler applicationScheduler;
    private TelegramCredentialsSaver telegramCredentialsSaver;

    @Autowired
    public Bot(TaskRunner taskRunner, CronPatternChanger cronPatternChanger, ApplicationScheduler applicationScheduler, TelegramCredentialsSaver telegramCredentialsSaver) {
        this.taskRunner = taskRunner;
        this.cronPatternChanger = cronPatternChanger;
        this.applicationScheduler = applicationScheduler;
        this.telegramCredentialsSaver = telegramCredentialsSaver;
    }

    @Override
    public String getBotUsername() {
        return "@nobodysShutterBot"; //TODO
    }

    @Override
    public String getBotToken() {
        return telegramCredentialsSaver.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                if (update.getMessage().getText().equals("/shutter")) {
                    try {
                        execute(sendMainBoard(update.getMessage().getChatId()));
                    } catch (TelegramApiException e) {
                        //TODO
                        e.printStackTrace();
                    }
                }
            }
        } else if (update.hasCallbackQuery()) {
            String messageKey = update.getCallbackQuery().getData();
            switch (messageKey) {
                case ("schedule_tapped"):
                    try {
                        execute(sendScheduleBoard(update.getCallbackQuery().getFrom().getId()));
                    } catch (TelegramApiException e) {
                        //TODO
                        e.printStackTrace();
                    }
                    break;
                case ("daily_tapped"):
                    sendDailyEarnings();
                    break;
                case ("month_tapped"):
                    sendMonthEarnings();
                    break;
                default:
                    changeSchedule(messageKey);//TODO check pattern
                    try {
                        execute(SendMessage.builder()
                                .text("Pattern was changed on " + messageKey)
                                .chatId(String.valueOf(update.getCallbackQuery().getFrom().getId()))
                                .build());
                    } catch (TelegramApiException e) {
                        e.printStackTrace(); // TODO
                    }
                    break;
            }
        }
    }

    private void changeSchedule(String pattern) {
        applicationScheduler.reSchedule(cronPatternChanger.setPattern(pattern));
    }

    private void sendDailyEarnings() {
        taskRunner.run();
    }

    private void sendMonthEarnings() {
        taskRunner.runMonth();
    }


    private SendMessage sendScheduleBoard(long chatId) {
        InlineKeyboardButton secondsInterval = new InlineKeyboardButton();
        InlineKeyboardButton minutesInterval = new InlineKeyboardButton();
        InlineKeyboardButton hourInterval = new InlineKeyboardButton();
        InlineKeyboardButton twoHourInterval = new InlineKeyboardButton();

        secondsInterval.setText("15sec");
        secondsInterval.setCallbackData("seconds");

        minutesInterval.setText("30min");
        minutesInterval.setCallbackData("minutesInterval");

        hourInterval.setText("hour");
        hourInterval.setCallbackData("hour");

        twoHourInterval.setText("2hours");
        twoHourInterval.setCallbackData("2hour");

        // ***   ***  ***   ***  ***   ***  //
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(secondsInterval);
        buttons.add(minutesInterval);
        buttons.add(hourInterval);
        buttons.add(twoHourInterval);

        // ***   ***  ***   ***  ***   ***  //
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(buttons);
        
        // ***   ***  ***   ***  ***   ***  //
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rowList);
        return SendMessage.builder()
                .chatId(String.valueOf(chatId))
                .text("Choose time")
                .replyMarkup(markup)
                .build();
    }

    private SendMessage sendMainBoard(long chatId) {
        InlineKeyboardButton scheduleButton = new InlineKeyboardButton();
        InlineKeyboardButton monthEarningsButton = new InlineKeyboardButton();
        InlineKeyboardButton dailyEarnings = new InlineKeyboardButton();

        scheduleButton.setText("Schedule");
        scheduleButton.setCallbackData("schedule_tapped");

        monthEarningsButton.setText("Month Earnings");
        monthEarningsButton.setCallbackData("month_tapped");

        dailyEarnings.setText("Daily Earnings");
        dailyEarnings.setCallbackData("daily_tapped");

        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(scheduleButton);
        buttons.add(monthEarningsButton);
        buttons.add(dailyEarnings);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(buttons);

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rowList);
        return SendMessage.builder()
                .chatId(String.valueOf(chatId))
                .text("Actions")
                .replyMarkup(markup)
                .build();
    }
}
