package com.nobody.saver;

import com.nobody.annotation.InjectTelegramCredentials;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@ToString
public class TelegramCredentialsSaver {

    @Getter
    @Setter
    @InjectTelegramCredentials
    private String botName;

    @Getter
    @Setter
    @InjectTelegramCredentials
    private String token;

    @Getter
    @Setter
    @InjectTelegramCredentials
    private String chatId;

}
