package com.nobody.mapper;

import com.nobody.dto.ScheduleSettingsDto;
import com.nobody.entity.ScheduleSettings;

public class SettingsMapper {

    public static ScheduleSettings dtoToEntity(ScheduleSettingsDto settingsDto) {
        return ScheduleSettings.builder()
                .name(settingsDto.getName())
                .value(settingsDto.getValue())
                .isCurrent(settingsDto.getIsCurrent())
                .build();
    }

    public static ScheduleSettingsDto entityToDto(ScheduleSettings settings) {
        return ScheduleSettingsDto.builder()
                .name(settings.getName())
                .value(settings.getValue())
                .isCurrent(settings.getIsCurrent())
                .build();
    }
}
