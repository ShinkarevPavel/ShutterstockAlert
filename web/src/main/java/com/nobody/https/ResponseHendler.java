package com.nobody.https;

import com.nobody.exception.ShutterStockResponseException;
import com.nobody.saver.ShutterHeaderSaver;
import com.nobody.util.DateTimeBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;


@Component
public class ResponseHendler {
    private static final Logger logger = LogManager.getLogger();
    private String URL = "https://submit.shutterstock.com/api/next/v2/earnings/aggregate?aggregation_period=day&year=%s&month=%s";
    private ShutterHeaderSaver saver;

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

        buildURL();
        HttpUriRequest request = new HttpGet(URL);
        try {
            HttpResponse httpResponse = httpClient.execute(configRequest(request));
            HttpEntity entity = httpResponse.getEntity();
            response = EntityUtils.toString(entity);
        } catch (IOException e) {
            logger.log(Level.ERROR, "ResponseHendler throws exception. Error. IO with Input stream working" + e.getMessage());
            throw new ShutterStockResponseException("ResponseHendler throws exception. Error. IO with Input stream working" + e.getMessage());
        }
        return response;
    }

    private void buildURL() {
        String year = DateTimeBuilder.getYear();
        String month = DateTimeBuilder.getMonth();
        this.URL = String.format(this.URL, year, month);
        logger.log(Level.DEBUG, "URL is " + URL);
    }

    private HttpUriRequest configRequest(HttpUriRequest request) {
        for (Map.Entry<String, String> header : saver.getHeaders().entrySet()) {
            if (header.getKey().equals("Cookie")) {
                if (header.getValue().length() == 0) {
                    logger.log(Level.DEBUG, "Cookie is null. Current time " + LocalDateTime.now());
                }
            }
            request.setHeader(header.getKey(), header.getValue());
        }
        return request;
    }
}
