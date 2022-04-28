package com.nobody.https;

import com.nobody.exception.ShutterStockResponseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PingHerokuServerHelper {

    private static final Logger logger = LogManager.getLogger();
    private final String URL = "https://shutter-application.herokuapp.com/api/v1/ping";
//    private final String URL = "http://localhost:8080/api/v1/ping";

    @Scheduled(cron = "0 */29 * * * *")
    public void sendRequest() {
        HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();
        HttpUriRequest request = new HttpGet(URL);
        try {
            httpClient.execute(request);
        } catch (IOException e) {
            logger.log(Level.INFO, "PingHerokuServerHelper throws exception. Error. IO with Input stream working" + e.getMessage());
            throw new ShutterStockResponseException("ResponseHendler throws exception. Error. IO with Input stream working" + e.getMessage());
        }
    }
}
