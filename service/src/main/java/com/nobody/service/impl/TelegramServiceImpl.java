package com.nobody.service.impl;

import com.nobody.dao.impl.TelegramDaoImpl;
import com.nobody.dto.TelegramBotCredentialsDto;
import com.nobody.entity.TelegramCredentials;
import com.nobody.exception.ShutterServiceException;
import com.nobody.mapper.TelegramCredentialsMapper;
import com.nobody.saver.TelegramCredentialsSaver;
import com.nobody.service.BaseEntityService;
import com.nobody.util.KeyValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TelegramServiceImpl implements BaseEntityService<TelegramBotCredentialsDto> {

    private static final Logger logger = LogManager.getLogger();
    private TelegramDaoImpl telegramDao;
    private TelegramCredentialsSaver telegramCredentialsSaver;

    @Autowired
    public TelegramServiceImpl(TelegramDaoImpl telegramDao, TelegramCredentialsSaver telegramCredentialsSaver) {
        this.telegramDao = telegramDao;
        this.telegramCredentialsSaver = telegramCredentialsSaver;
    }

    @Override
    @Transactional
    public void addEntity(TelegramBotCredentialsDto telegramBotCredentialsDto) {
        checkAccessKey(telegramBotCredentialsDto.getAccessKey());

        telegramDao.getEntity(telegramBotCredentialsDto.getToken())
                .ifPresent(c -> {
                    throw new ShutterServiceException("Token " + telegramBotCredentialsDto.getToken() + " is exist.");
                });

        // made previous credentials not active
        checkAndTurnOffActiveCredentials();
        telegramDao.addEntity(TelegramCredentialsMapper.dtoToEntity(telegramBotCredentialsDto));
        // setting credentials in saver
        setCredentials(telegramBotCredentialsDto);
    }

    @Override
    @Transactional
    public void removeEntity(TelegramBotCredentialsDto telegramBotCredentialsDto) {
        checkAccessKey(telegramBotCredentialsDto.getAccessKey());

        TelegramCredentials telegramCredentials = getIfExists(telegramBotCredentialsDto.getToken());
        telegramDao.removeEntity(telegramCredentials.getId());
        //remove credentials from telegram saver
        removeCredentials();
    }

    @Override
    @Transactional
    public TelegramBotCredentialsDto updateEntity(TelegramBotCredentialsDto telegramBotCredentialsDto) {
        checkAccessKey(telegramBotCredentialsDto.getAccessKey());
        TelegramCredentials telegramCredentials = getIfExists(telegramBotCredentialsDto.getToken());
        telegramCredentials.setToken(telegramBotCredentialsDto.getToken());
        telegramCredentials.setChatId(telegramBotCredentialsDto.getChatId() != null ?
                telegramBotCredentialsDto.getChatId() : telegramCredentials.getChatId());

        setCredentials(TelegramCredentialsMapper.entityToDto((telegramCredentials))); // update telegram credentials in saver
        return TelegramCredentialsMapper.entityToDto(telegramDao.updateEntity(telegramCredentials));
    }

    @Override
    public TelegramBotCredentialsDto getEntity(TelegramBotCredentialsDto telegramBotCredentialsDto) {
        checkAccessKey(telegramBotCredentialsDto.getAccessKey());
        TelegramCredentials telegramCredentials = getIfExists(telegramBotCredentialsDto.getToken());
        return TelegramCredentialsMapper.entityToDto(telegramCredentials);
    }

    @Override
    public List<TelegramBotCredentialsDto> getAll() {
        List<TelegramBotCredentialsDto> collect = telegramDao.getAll()
                .stream().map(TelegramCredentialsMapper::entityToDto)
                .collect(Collectors.toList());
        collect.forEach(c -> c.setToken("hidden"));
        return collect;
    }

    @Transactional
    public void changeAvailability(TelegramBotCredentialsDto telegramBotCredentialsDto) {
        checkAccessKey(telegramBotCredentialsDto.getAccessKey());
        checkAndTurnOffActiveCredentials();
        telegramDao.getEntity(telegramBotCredentialsDto.getToken())
                .orElseThrow(() -> new ShutterServiceException("There is no credentials " + telegramBotCredentialsDto.getToken()))
                .setIsActive(true);
    }

    private TelegramCredentials getIfExists(String parameter) {
        return telegramDao.getEntity(parameter)
                .orElseThrow(() -> new ShutterServiceException("There is no entity with " + parameter));
    }

    private void checkAndTurnOffActiveCredentials() {
        Optional<TelegramCredentials> active = telegramDao.getActive();
        if (active.isEmpty()) {
            logger.log(Level.ERROR, "There is no active credentials");
        } else {
            active.get().setIsActive(false);
        }
    }

    private void checkAccessKey(String key) {
        if (!KeyValidator.isKeyValid(key)) {
            throw new ShutterServiceException("Security key error");
        }
    }

    private void setCredentials(TelegramBotCredentialsDto credentials) {
        telegramCredentialsSaver.setToken(credentials.getToken());
        telegramCredentialsSaver.setChatId(credentials.getChatId());
    }

    private void removeCredentials() {
        telegramCredentialsSaver.setToken(null);
        telegramCredentialsSaver.setChatId(null);
    }
}
