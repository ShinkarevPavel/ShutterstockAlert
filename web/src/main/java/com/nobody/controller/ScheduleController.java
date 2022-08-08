package com.nobody.controller;

import com.nobody.dto.ScheduleSettingsDto;
import com.nobody.runner.ApplicationScheduler;
import com.nobody.service.impl.ScheduleSettingsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/schedule")
public class ScheduleController {

    private ScheduleSettingsServiceImpl scheduleSettingsService;
    private ApplicationScheduler applicationScheduler;

    @Autowired
    public ScheduleController(ScheduleSettingsServiceImpl scheduleSettingsService, ApplicationScheduler applicationScheduler) {
        this.scheduleSettingsService = scheduleSettingsService;
        this.applicationScheduler = applicationScheduler;
    }

    @PatchMapping("/change")
    @ResponseStatus(HttpStatus.OK)
    public void changeSchedule(@Validated(ScheduleSettingsDto.Change.class) @RequestBody ScheduleSettingsDto scheduleSettingsDto) {
        String pattern = scheduleSettingsService.changePattern(scheduleSettingsDto);
        applicationScheduler.reSchedule(pattern);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public void createSchedule(@Validated(ScheduleSettingsDto.Create.class) @RequestBody ScheduleSettingsDto scheduleSettingsDto) {
        scheduleSettingsService.addEntity(scheduleSettingsDto);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateSchedule(@Validated(ScheduleSettingsDto.Update.class)@RequestBody ScheduleSettingsDto scheduleSettingsDto) {
        scheduleSettingsService.updateEntity(scheduleSettingsDto);
    }
}
