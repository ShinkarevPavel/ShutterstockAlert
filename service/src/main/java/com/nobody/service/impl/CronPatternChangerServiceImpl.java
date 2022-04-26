package com.nobody.service.impl;

import com.nobody.dto.ScheduleDto;
import com.nobody.exception.ShutterServiceException;
import com.nobody.service.ParentService;
import com.nobody.util.CronPatternChanger;
import com.nobody.util.KeyValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CronPatternChangerServiceImpl implements ParentService {

    private CronPatternChanger patternChanger;

    @Autowired
    public CronPatternChangerServiceImpl(CronPatternChanger patternChanger) {
        this.patternChanger = patternChanger;
    }

    public String changePattern(ScheduleDto scheduleDto) {
        if (!KeyValidator.isKeyValid(scheduleDto.getAccessKey())) {
            throw new ShutterServiceException("Security key error");
        }
        return patternChanger.setPattern(scheduleDto.getCode());
    }
}
