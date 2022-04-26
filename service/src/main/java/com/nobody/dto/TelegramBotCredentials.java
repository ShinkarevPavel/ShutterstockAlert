package com.nobody.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TelegramBotCredentials extends BaseDto{
    private String accessKey;
    private String token;
    private String chatId;
}
