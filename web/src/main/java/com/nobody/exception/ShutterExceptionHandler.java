package com.nobody.exception;

import com.nobody.dto.ErrorDto;
import com.nobody.sendler.MessageToTelegramSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
        response.put("message", e.getLocalizedMessage());
        response.put("error_code", HttpStatus.NO_CONTENT.value());
        sendMessageToTelegram(e.getMessage(), HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, Object> response = new HashMap<>();
        String message = resolveBindingResultErrors(e.getBindingResult());
        response.put("message", message);
        response.put("error_code", HttpStatus.BAD_REQUEST.value());
        sendMessageToTelegram(e.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private String resolveBindingResultErrors(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .map(fr -> {
                    String field = fr.getField();
                    String validationMessage = fr.getDefaultMessage();
                    return String.format("'%s': %s", field, validationMessage);
                })
                .collect(Collectors.joining(", "));
    }

    private void sendMessageToTelegram(String exMessage, HttpStatus code) {
        sender.sendMessage(ErrorDto.builder()
                .message("Action with your application")
                .exceptionMessage(exMessage)
                .errorCode(String.valueOf(code.value()))
                .build());
    }
}
