package com.nobody.https;

import com.nobody.exception.ShutterStockResponseException;
import com.nobody.header.ShutterHeaderSaver;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;


@Component
public class ResponseHendler {
    private static final Logger logger = LogManager.getLogger();
    private ShutterHeaderSaver saver;
    private final String URL = "https://submit.shutterstock.com/earnings";

    @Autowired
    public ResponseHendler(ShutterHeaderSaver saver) {
        this.saver = saver;
    }

    public String sendRequest() {
        String response;
        HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();
        HttpUriRequest request = new HttpGet(URL);
        try {
            HttpResponse httpResponse = httpClient.execute(configRequest(request));
            HttpEntity entity = httpResponse.getEntity();
            InputStream content = entity.getContent();
            response = IOUtils.toString(content, StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.log(Level.INFO, "ResponseHendler throws exception. Error. IO with Input stream working" + e.getMessage());
            throw new ShutterStockResponseException("ResponseHendler throws exception. Error. IO with Input stream working" + e.getMessage());
        }
        return response;
    }

    private HttpUriRequest configRequest(HttpUriRequest request) {
        for (Map.Entry<String, String> header : saver.getHeaders().entrySet()) {
            request.setHeader(header.getKey(), header.getValue());
        }
        return request;
    }
}
