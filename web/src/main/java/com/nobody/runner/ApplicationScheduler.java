package com.nobody.runner;

import com.nobody.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ScheduledFuture;

@Component
public class ApplicationScheduler {
    private final String LAUNCH_CRON = "0 0 * * * *";
    private ScheduledFuture scheduledFuture;
    private ConcurrentTaskScheduler taskScheduler;

    private TaskRunner taskRunner;
    private ShutterExceptionHandler shutterExceptionHandler;

    @Autowired
    public ApplicationScheduler(TaskRunner taskRunner, ShutterExceptionHandler shutterExceptionHandler) {
        this.taskRunner = taskRunner;
        this.shutterExceptionHandler = shutterExceptionHandler;
    }

    public void reSchedule(String cronExpressionStr) {
        if (taskScheduler == null) {
            this.taskScheduler = new ConcurrentTaskScheduler();
            this.taskScheduler.setErrorHandler(throwable -> {
                if (throwable instanceof ShutterStockParseException) {
                    this.shutterExceptionHandler.handleShutterStockParseException((ShutterStockParseException) throwable);
                } else if (throwable instanceof ShutterStockResponseException) {
                    this.shutterExceptionHandler.handleShutterStockResponseException((ShutterStockResponseException) throwable);
                }
            });
        }
        if (this.scheduledFuture != null) {
            this.scheduledFuture.cancel(true);
        }
        this.scheduledFuture = this.taskScheduler.schedule(taskRunner, new CronTrigger(cronExpressionStr));
    }

    @PostConstruct
    public void initializeScheduler() {
        this.reSchedule(LAUNCH_CRON);
    }
}
