package com.nobody.sendler;

import com.nobody.dto.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Component
public class MessageToTelegramSender {
    private static final Logger logger = LogManager.getLogger();
    private String TOKEN = "";
    private String CHAT_ID = "";

    public void sendMessage(BaseDto dto) {
        if (!checkTelegramBotCredentials()) {
            logger.log(Level.ERROR, "Error from MessageToTelegramSender. Message can't be send. Token or chat id is not set.");
        }
        String text;
        if (dto instanceof ErrorDto) {
            text = "❗️";
        } else {
            text = "✅";
        }
        text += dto.toString();
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_2)
                .build();
        UriBuilder builder = UriBuilder
                .fromUri("https://api.telegram.org")
                .path("/{token}/sendMessage")
                .queryParam("chat_id", this.CHAT_ID)
                .queryParam("text", text);

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(builder.build("bot" + this.TOKEN))
                .timeout(Duration.ofSeconds(5))
                .build();
        try {
            HttpResponse<String> response = httpClient
                    .send(request, HttpResponse.BodyHandlers.ofString());
            logger.log(Level.INFO, response.statusCode());
        } catch (IOException | InterruptedException e) {
            logger.log(Level.ERROR, "Error from MessageToTelegramSender " + e.getMessage());
        }
    }

    public void setCredentials(TelegramBotCredentials credentials) {
        this.TOKEN = credentials.getToken();
        this.CHAT_ID = credentials.getChatId();
    }

    private boolean checkTelegramBotCredentials() {
        return this.TOKEN != null && this.CHAT_ID != null && this.TOKEN.length() != 0 && this.CHAT_ID.length() != 0;
    }
}
