package com.nobody.service.impl;

import com.nobody.dao.impl.HeaderDaoImpl;
import com.nobody.dto.UpdateOrSetHeaderDto;
import com.nobody.entity.Header;
import com.nobody.exception.ShutterServiceException;
import com.nobody.header.ShutterHeaderSaver;
import com.nobody.mapper.HeaderMapper;
import com.nobody.service.BaseEntityService;
import com.nobody.util.KeyValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HeaderServiceImpl implements BaseEntityService<UpdateOrSetHeaderDto> {

    private HeaderDaoImpl headerDao;
    private ShutterHeaderSaver saver;


    @Autowired
    public HeaderServiceImpl(HeaderDaoImpl headerDao, ShutterHeaderSaver saver) {
        this.headerDao = headerDao;
        this.saver = saver;
    }

    @Override
    public List<UpdateOrSetHeaderDto> getAll() {
        List<UpdateOrSetHeaderDto> collect = headerDao.getAll().stream()
                .map(HeaderMapper::entityToDto)
                .collect(Collectors.toList());
        collect.stream().filter(d -> d.getHeaderParameter().equals("Cookie")).forEach(d -> d.setHeaderValue("hidden"));
        return collect;
    }

    @Override
    @Transactional
    public void addEntity(UpdateOrSetHeaderDto addHeader) {
        checkKey(addHeader.getAccessKey());
        headerDao.getEntity(addHeader.getHeaderParameter()).ifPresent(s -> {
            throw new ShutterServiceException("Header " + addHeader.getHeaderParameter() + " is exist");
        });
        headerDao.addEntity(HeaderMapper.dtoToHeader(addHeader));
        addHeaderToHeaderSaver(addHeader.getHeaderParameter(), addHeader.getHeaderValue());
    }

    @Override
    @Transactional
    public void removeEntity(UpdateOrSetHeaderDto removeHeader) {
        checkKey(removeHeader.getAccessKey());
        Header header = headerDao.getEntity(removeHeader.getHeaderParameter())
                .orElseThrow(() -> new ShutterServiceException("There is no " + removeHeader.getHeaderParameter() + " header"));
        headerDao.removeEntity(header.getId());
        removeHeaderFromHeaderSaver(removeHeader.getHeaderParameter());
    }

    @Override
    @Transactional
    public UpdateOrSetHeaderDto updateEntity(UpdateOrSetHeaderDto updateHeader) {
        checkKey(updateHeader.getAccessKey());
        Header header = headerDao.getEntity(updateHeader.getHeaderParameter())
                .orElseThrow(() -> new ShutterServiceException("There is no " + updateHeader.getHeaderParameter() + " header"));
        header.setValue(updateHeader.getHeaderValue());
        updateHeaderIntoHeaderSaver(updateHeader.getHeaderParameter(), updateHeader.getHeaderValue());
        return HeaderMapper.entityToDto(headerDao.updateEntity(header));
    }

    @Override
    public UpdateOrSetHeaderDto getEntity(UpdateOrSetHeaderDto updateOrSetHeaderDto) {
        checkKey(updateOrSetHeaderDto.getAccessKey());
        Header header = headerDao.getEntity(updateOrSetHeaderDto.getHeaderParameter())
                        .orElseThrow(() -> new ShutterServiceException("There is no " + updateOrSetHeaderDto.getHeaderParameter() + " header"));
        return HeaderMapper.entityToDto(header);
    }

    private void checkKey(String key) {
        if (!KeyValidator.isKeyValid(key)) {
            throw new ShutterServiceException("Security key error");
        }
    }

    private void addHeaderToHeaderSaver(String headerParameter, String headerValue) {
        saver.getHeaders().put(headerParameter, headerValue);
    }
    private void removeHeaderFromHeaderSaver(String headerParameter) {
        saver.getHeaders().remove(headerParameter);
    }

    private void updateHeaderIntoHeaderSaver(String headerParameter, String headerValue) {
        saver.getHeaders().replace(headerParameter, headerValue);
    }
}
