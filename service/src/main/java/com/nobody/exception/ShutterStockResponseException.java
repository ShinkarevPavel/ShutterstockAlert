package com.nobody.exception;

public class ShutterStockResponseException extends RuntimeException {

    public ShutterStockResponseException() {
    }

    public ShutterStockResponseException(String message) {
        super(message);
    }

    public ShutterStockResponseException(String message, Throwable cause) {
        super(message, cause);
    }
}
