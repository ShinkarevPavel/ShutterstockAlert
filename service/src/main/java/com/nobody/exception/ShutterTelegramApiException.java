package com.nobody.exception;

public class ShutterTelegramApiException extends RuntimeException {

    public ShutterTelegramApiException() {
    }

    public ShutterTelegramApiException(String message) {
        super(message);
    }

    public ShutterTelegramApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
