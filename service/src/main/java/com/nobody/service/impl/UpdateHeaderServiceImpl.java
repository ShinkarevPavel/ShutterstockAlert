package com.nobody.service.impl;

import com.nobody.dto.UpdateOrSetHeaderDto;
import com.nobody.header.ShutterHeaderSaver;
import com.nobody.service.ParentService;
import com.nobody.util.KeyValidator;
import com.nobody.exception.ShutterServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateHeaderServiceImpl implements ParentService {

    private ShutterHeaderSaver saver;

    @Autowired
    public UpdateHeaderServiceImpl(ShutterHeaderSaver saver) {
        this.saver = saver;
    }

    public UpdateOrSetHeaderDto updateHeader(UpdateOrSetHeaderDto updateOrSetHeaderDto) {
        checkData(updateOrSetHeaderDto);

        this.saver.getHeaders().replace(updateOrSetHeaderDto.getHeaderParameter(), updateOrSetHeaderDto.getHeaderValue());
        return UpdateOrSetHeaderDto.builder()
                .accessKey("-* hidden *-")
                .headerParameter(updateOrSetHeaderDto.getHeaderParameter())
                .headerValue(this.saver.getHeaders().get(updateOrSetHeaderDto.getHeaderParameter()))
                .build();
    }

    public void removeHeader(UpdateOrSetHeaderDto updateOrSetHeaderDto) {
        checkData(updateOrSetHeaderDto);
        saver.getHeaders().remove(updateOrSetHeaderDto.getHeaderParameter(), updateOrSetHeaderDto.getHeaderValue());
    }

    private void checkData(UpdateOrSetHeaderDto updateOrSetHeaderDto) {
        if (!KeyValidator.isKeyValid(updateOrSetHeaderDto.getAccessKey())) {
            throw new ShutterServiceException("Security key error");
        }

        if (!this.saver.getHeaders().containsKey(updateOrSetHeaderDto.getHeaderParameter())) {
            throw new ShutterServiceException("Header parameter is wrong");
        }
    }
}
