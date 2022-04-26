package com.nobody.service.impl;

import com.nobody.dto.TelegramBotCredentials;
import com.nobody.exception.ShutterServiceException;
import com.nobody.service.ParentService;
import com.nobody.util.KeyValidator;
import org.springframework.stereotype.Service;

@Service
public class TelegramServiceImpl implements ParentService {

    public void setTelegramCredentials(TelegramBotCredentials credentials) {
        if (!KeyValidator.isKeyValid(credentials.getAccessKey())) {
            throw new ShutterServiceException("Security key error");
        }
        // here should be logic for setting credentials to DB. Later :)
    }
}
