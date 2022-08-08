package com.nobody.service.impl;

import com.nobody.dao.impl.ScheduleSettingsDaoImpl;
import com.nobody.dto.ScheduleSettingsDto;
import com.nobody.entity.ScheduleSettings;
import com.nobody.exception.ShutterServiceException;
import com.nobody.mapper.SettingsMapper;
import com.nobody.service.BaseEntityService;
import com.nobody.util.CronPatternChanger;
import com.nobody.util.KeyValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ScheduleSettingsServiceImpl implements BaseEntityService<ScheduleSettingsDto> {

    private CronPatternChanger patternChanger;
    private ScheduleSettingsDaoImpl settingsDao;

    @Autowired
    public ScheduleSettingsServiceImpl(CronPatternChanger patternChanger, ScheduleSettingsDaoImpl settingsDao) {
        this.patternChanger = patternChanger;
        this.settingsDao = settingsDao;
    }

    @Transactional
    public String changePattern(ScheduleSettingsDto scheduleSettingsDto) {
        checkKey(scheduleSettingsDto.getAccessKey());
        ScheduleSettings settings = returnIfExist(scheduleSettingsDto.getName());
        changeCurrentStatusOnFalseInSettings();
        setCurrent(settings);
        return settings.getValue();
    }


    @Transactional
    @Override
    public void addEntity(ScheduleSettingsDto scheduleSettingsDto) {
        checkKey(scheduleSettingsDto.getAccessKey());
        settingsDao.getEntity(scheduleSettingsDto.getName())
                .ifPresent((s) -> {
                    throw new ShutterServiceException("Setting with name " + scheduleSettingsDto.getName() + " exists.");
                });

        if (Objects.nonNull(scheduleSettingsDto.getIsCurrent()) && scheduleSettingsDto.getIsCurrent()) {
            changeCurrentStatusOnFalseInSettings();
        }
        settingsDao.addEntity(SettingsMapper.dtoToEntity(scheduleSettingsDto));
    }

    @Transactional
    @Override
    public void removeEntity(ScheduleSettingsDto scheduleSettingsDto) {

    }

    @Transactional
    @Override
    public ScheduleSettingsDto updateEntity(ScheduleSettingsDto scheduleSettingsDto) {
        checkKey(scheduleSettingsDto.getAccessKey());
        ScheduleSettings settings = returnIfExist(scheduleSettingsDto.getName());
        settings.setName(scheduleSettingsDto.getName());
        settings.setValue(scheduleSettingsDto.getValue());

        if (scheduleSettingsDto.getIsCurrent()) {
            changeCurrentStatusOnFalseInSettings();
            settings.setIsCurrent(true);
        } else {
            settings.setIsCurrent(false);
        }
        return scheduleSettingsDto;
    }

    @Override
    public ScheduleSettingsDto getEntity(ScheduleSettingsDto scheduleSettingsDto) {
        checkKey(scheduleSettingsDto.getAccessKey());
        return SettingsMapper.entityToDto(returnIfExist(scheduleSettingsDto.getName()));
    }

    @Override
    public List<ScheduleSettingsDto> getAll() {
        return null;
    }

    public void setCurrent(ScheduleSettings settings) {
        // this method should change schedule status in DB
        settings.setIsCurrent(true);
    }

    private void changeCurrentStatusOnFalseInSettings() {
        settingsDao.changeStatusOnFalse();
    }

    private ScheduleSettings returnIfExist(String name) {
        return settingsDao.getEntity(name)
                .orElseThrow(() ->
                        new ShutterServiceException("There is no entity with " + name + " name."));
    }

    private void checkKey(String key) {
        if (!KeyValidator.isKeyValid(key)) {
            throw new ShutterServiceException("Security key error");
        }
    }
}
