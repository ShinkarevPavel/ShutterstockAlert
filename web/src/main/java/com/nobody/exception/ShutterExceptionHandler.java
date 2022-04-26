package com.nobody.exception;

import com.nobody.dto.ErrorDto;
import com.nobody.sendler.MessageToTelegramSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ShutterExceptionHandler  {

    private MessageToTelegramSender sender;

    @Autowired
    public ShutterExceptionHandler(MessageToTelegramSender sender) {
        this.sender = sender;
    }

    @ExceptionHandler(ShutterServiceException.class)
    public ResponseEntity<Object> handleShutterServiceException(ShutterServiceException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", e.getMessage());
        response.put("error_code", HttpStatus.FORBIDDEN.value());
        sendMessageToTelegram(e.getMessage(), HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ShutterStockParseException.class)
    public ResponseEntity<Object> handleShutterStockParseException(ShutterStockParseException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", e.getMessage());
        response.put("error_code", HttpStatus.NO_CONTENT.value());
        sendMessageToTelegram(e.getMessage(), HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }


    @ExceptionHandler(ShutterStockResponseException.class)
    public ResponseEntity<Object> handleShutterStockResponseException(ShutterStockResponseException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", e.getMessage());
        response.put("error_code", HttpStatus.NO_CONTENT.value());
        sendMessageToTelegram(e.getMessage(), HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    private void sendMessageToTelegram(String exMessage, HttpStatus code) {
        sender.sendMessage(ErrorDto.builder()
                .message("Action with your application")
                .exceptionMessage(exMessage)
                .errorCode(String.valueOf(code.value()))
                .build());
    }
}
