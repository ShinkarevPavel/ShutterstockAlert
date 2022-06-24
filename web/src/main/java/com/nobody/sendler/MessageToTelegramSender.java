package com.nobody.sendler;

import com.nobody.dto.BaseDto;
import com.nobody.saver.TelegramCredentialsSaver;
import com.nobody.sendler.util.PrepareMessageForTelegramFromDto;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Component
public class MessageToTelegramSender {
    private final Logger logger = LogManager.getLogger();
    private TelegramCredentialsSaver telegramCredentialsSaver;

    @Autowired
    public MessageToTelegramSender(TelegramCredentialsSaver telegramCredentialsSaver) {
        this.telegramCredentialsSaver = telegramCredentialsSaver;
    }

    public void sendMessage(BaseDto dto) {
        String text = buildTextMessage(dto);
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_2)
                .build();
        UriBuilder builder = UriBuilder
                .fromUri("https://api.telegram.org")
                .path("/{token}/sendMessage")
                .queryParam("chat_id", telegramCredentialsSaver.getChatId())
                .queryParam("text", text);

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(builder.build("bot" + telegramCredentialsSaver.getToken()))
                .timeout(Duration.ofSeconds(5))
                .build();
        try {
            HttpResponse<String> response = httpClient
                    .send(request, HttpResponse.BodyHandlers.ofString());
            logger.log(Level.INFO, "Message was sent. Status " + response.statusCode());
        } catch (IOException | InterruptedException e) {
            logger.log(Level.ERROR, "Error from MessageToTelegramSender " + e.getMessage());
        }
    }

    private String buildTextMessage(BaseDto dto) {
        return PrepareMessageForTelegramFromDto.buildMessage(dto);
    }
}
