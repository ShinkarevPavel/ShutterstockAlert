package com.nobody.saver;

import com.nobody.annotation.InjectHeaders;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ToString
public class ShutterHeaderSaver {
    @Getter
    @Setter
    @InjectHeaders
    private Map<String, String> headers;
}
