package com.nobody.annotation;

import com.nobody.dao.impl.TelegramDaoImpl;
import com.nobody.entity.TelegramCredentials;
import com.nobody.exception.ShutterServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Optional;

@Component
public class InjectTelegramCredentialsPostProcessor implements BeanPostProcessor {
    private static final Logger logger = LogManager.getLogger();
    private TelegramDaoImpl telegramDao;
    private TelegramCredentials telegramCredentials;

    @Autowired
    public InjectTelegramCredentialsPostProcessor(TelegramDaoImpl telegramDao) {
        this.telegramDao = telegramDao;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(InjectTelegramCredentials.class)) {
                if (field.getName().equals("TOKEN")) {
                    setCredentials();
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, bean, telegramCredentials.getToken());
                }

                if (field.getName().equals("CHAT_ID")) {
                    setCredentials();
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, bean, telegramCredentials.getChatId());
                }
            }
        }
        return bean;
    }

    private void setCredentials() {
        if (telegramCredentials == null) {
            getCredentials();
        }
    }

    private void getCredentials() {
        Optional<TelegramCredentials> activeTelegramCredentials = telegramDao.getActive();

        if (activeTelegramCredentials.isEmpty()) {
            logger.log(Level.ERROR, "Error from MessageToTelegramSender. Message can't be send. Token or chat will set with default values.");
            this.telegramCredentials = TelegramCredentials.builder()
                    .token("default")
                    .chatId("default")
                    .build();
        } else {
            telegramCredentials = activeTelegramCredentials.get();
        }
//        this.telegramCredentials = telegramCredentials
//                .orElseThrow(() -> new ShutterServiceException("There is no active telegram credentials"));


    }
}
