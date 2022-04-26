package com.nobody.controller;

import com.nobody.dto.ScheduleDto;
import com.nobody.runner.ApplicationScheduler;
import com.nobody.service.impl.CronPatternChangerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ScheduleController {


    private CronPatternChangerServiceImpl cronPatternChangerService;
    private ApplicationScheduler applicationScheduler;

    @Autowired
    public ScheduleController(CronPatternChangerServiceImpl cronPatternChangerService, ApplicationScheduler applicationScheduler) {
        this.cronPatternChangerService = cronPatternChangerService;
        this.applicationScheduler = applicationScheduler;
    }


    @PostMapping("/schedule")
    @ResponseStatus(HttpStatus.OK)
    public void updateScheduleRun(@RequestBody ScheduleDto scheduleDto) {
        String pattern = cronPatternChangerService.changePattern(scheduleDto);
        applicationScheduler.reSchedule(pattern);
    }


}
