package com.nobody.runner;

import com.nobody.exception.ShutterExceptionHandler;
import com.nobody.exception.ShutterStockParseException;
import com.nobody.exception.ShutterStockResponseException;
import com.nobody.service.impl.ScheduleSettingsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ScheduledFuture;

@Component
public class ApplicationScheduler {
  private ScheduledFuture scheduledFuture;
  private ConcurrentTaskScheduler taskScheduler;

  private TaskRunner taskRunner;
  private ShutterExceptionHandler shutterExceptionHandler;
  private ScheduleSettingsServiceImpl settingsService;

  @Autowired
  public ApplicationScheduler(
      TaskRunner taskRunner,
      ShutterExceptionHandler shutterExceptionHandler,
      ScheduleSettingsServiceImpl settingsService) {
    this.taskRunner = taskRunner;
    this.shutterExceptionHandler = shutterExceptionHandler;
    this.settingsService = settingsService;
  }

  public void reSchedule(String cronExpressionStr) {
    if (taskScheduler == null) {
      this.taskScheduler = new ConcurrentTaskScheduler();
      this.taskScheduler.setErrorHandler(
          throwable -> {
            if (throwable instanceof ShutterStockParseException) {
              this.shutterExceptionHandler.handleShutterStockParseException(
                  (ShutterStockParseException) throwable);
            } else if (throwable instanceof ShutterStockResponseException) {
              this.shutterExceptionHandler.handleShutterStockResponseException(
                  (ShutterStockResponseException) throwable);
            }
          });
    }

    if (this.scheduledFuture != null) {
      this.scheduledFuture.cancel(true);
    }
    this.scheduledFuture =
        this.taskScheduler.schedule(taskRunner, new CronTrigger(cronExpressionStr));
  }

  @PostConstruct
  public void initializeScheduler() {
    this.reSchedule(settingsService.getCurrentSetting());
  }
}
