package com.nobody.mapper;

import com.nobody.dto.TelegramBotCredentialsDto;
import com.nobody.entity.TelegramCredentials;

public class TelegramCredentialsMapper {
    public static TelegramCredentials dtoToEntity(TelegramBotCredentialsDto telegramBotCredentialsDto) {
        return TelegramCredentials.builder()
                .token(telegramBotCredentialsDto.getToken())
                .chatId(telegramBotCredentialsDto.getChatId())
                .isActive(true)
                .build();
    }

    public static TelegramBotCredentialsDto entityToDto(TelegramCredentials telegramCredentials) {
        return TelegramBotCredentialsDto.builder()
                .accessKey("hidden")
                .token(telegramCredentials.getToken())
                .chatId(telegramCredentials.getChatId())
                .build();
    }
}
