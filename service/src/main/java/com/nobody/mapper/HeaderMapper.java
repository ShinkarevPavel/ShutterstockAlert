package com.nobody.mapper;

import com.nobody.dto.UpdateOrSetHeaderDto;
import com.nobody.entity.Header;

public class HeaderMapper {

    public static Header dtoToHeader(UpdateOrSetHeaderDto headerDto) {
        return Header.builder()
                .key(headerDto.getHeaderParameter())
                .value(headerDto.getHeaderValue())
                .build();
    }

    public static UpdateOrSetHeaderDto entityToDto(Header header) {
        return UpdateOrSetHeaderDto.builder()
                .accessKey("hidden")
                .headerParameter(header.getKey())
                .headerValue(header.getValue())
                .build();
    }
}
