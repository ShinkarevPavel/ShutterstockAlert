package com.nobody.sendler.api;

import com.nobody.exception.ShutterTelegramApiException;
import com.nobody.runner.ApplicationScheduler;
import com.nobody.runner.TaskRunner;
import com.nobody.saver.TelegramCredentialsSaver;
import com.nobody.service.impl.ScheduleSettingsServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
  private final Logger logger = LogManager.getLogger();
  private TaskRunner taskRunner;
  private ScheduleSettingsServiceImpl settingsService;
  private ApplicationScheduler applicationScheduler;
  private TelegramCredentialsSaver telegramCredentialsSaver;

  @Autowired
  public Bot(
      TaskRunner taskRunner,
      ScheduleSettingsServiceImpl settingsService,
      ApplicationScheduler applicationScheduler,
      TelegramCredentialsSaver telegramCredentialsSaver) {
    this.taskRunner = taskRunner;
    this.settingsService = settingsService;
    this.applicationScheduler = applicationScheduler;
    this.telegramCredentialsSaver = telegramCredentialsSaver;
  }

  @Override
  public String getBotUsername() {
    return telegramCredentialsSaver.getBotName();
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
            throw new ShutterTelegramApiException("Error of sending message.", e);
          }
        }
      }
    }

    if (update.hasCallbackQuery()) {
      Long id = update.getCallbackQuery().getFrom().getId();
      logger.log(Level.INFO, id + " user trying to get access.");
      String messageKey = update.getCallbackQuery().getData();
      if (String.valueOf(id).equals(this.telegramCredentialsSaver.getChatId())) {
        switch (messageKey) {
          case ("schedule_tapped"):
            try {
              execute(sendScheduleBoard(id));
            } catch (TelegramApiException e) {
              throw new ShutterTelegramApiException("Error of sending message.", e);
            }
            break;
          case ("daily_tapped"):
            sendDailyEarnings();
            break;
          case ("month_tapped"):
            sendMonthEarnings();
            break;
          default:
            changeSchedule(messageKey);
            try {
              execute(
                  SendMessage.builder()
                      .text("Pattern was changed on " + messageKey)
                      .chatId(String.valueOf(id))
                      .build());
            } catch (TelegramApiException e) {
              throw new ShutterTelegramApiException("Error of sending message.", e);
            }
            break;
        }
      } else {
        try {
          execute(accessDenied(id));
          execute(alertToAdmin(update));
        } catch (TelegramApiException e) {
          throw new ShutterTelegramApiException("Error of sending message.", e);
        }
      }
    }
  }

  private SendMessage sendScheduleBoard(long chatId) {
    InlineKeyboardButton secondsInterval = new InlineKeyboardButton();
    InlineKeyboardButton minutesInterval = new InlineKeyboardButton();
    InlineKeyboardButton hourInterval = new InlineKeyboardButton();
    InlineKeyboardButton twoHourInterval = new InlineKeyboardButton();

    secondsInterval.setText("15 Seconds");
    secondsInterval.setCallbackData("Seconds");

    minutesInterval.setText("30 minutes");
    minutesInterval.setCallbackData("halfHour");

    hourInterval.setText("1 Hour");
    hourInterval.setCallbackData("hour");

    twoHourInterval.setText("2 Hours");
    twoHourInterval.setCallbackData("2hour");

    // ***   ***  ***   ***  ***   *** //
    List<InlineKeyboardButton> buttons = new ArrayList<>();
    buttons.add(secondsInterval);
    buttons.add(minutesInterval);
    buttons.add(hourInterval);
    buttons.add(twoHourInterval);

    // ***   ***  ***   ***  ***   *** //
    List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
    rowList.add(buttons);

    // ***   ***  ***   ***  ***   *** //
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

  private SendMessage accessDenied(Long chatId) {
    return SendMessage.builder().chatId(String.valueOf(chatId)).text("Access Denied.").build();
  }

  private SendMessage alertToAdmin(Update update) {
    String userName = update.getCallbackQuery().getFrom().getUserName();
    String firstName = update.getCallbackQuery().getFrom().getFirstName();
    String lastName = update.getCallbackQuery().getFrom().getLastName();

    String message = "User with %s is trying to control bot.";
    String data =
        (userName != null ? "username - " + userName : "")
            + "\n"
            + (firstName != null ? "firstName - " + firstName : "")
            + "\n"
            + (lastName != null ? "lastname - " + lastName : "")
            + "\n";

    message = String.format(message, data);

    return SendMessage.builder()
        .chatId(String.valueOf(telegramCredentialsSaver.getChatId()))
        .text(message)
        .build();
  }

  private void changeSchedule(String pattern) {
    applicationScheduler.reSchedule(settingsService.getPatternAndSetItCurrent(pattern));
    logger.log(Level.INFO, "Pattern was changed. Current pattern is " + pattern);
  }

  private void sendDailyEarnings() {
    taskRunner.run();
  }

  private void sendMonthEarnings() {
    taskRunner.runMonth();
  }
}
