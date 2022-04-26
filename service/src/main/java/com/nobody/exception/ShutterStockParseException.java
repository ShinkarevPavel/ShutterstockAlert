package com.nobody.exception;

public class ShutterStockParseException extends RuntimeException{
    public ShutterStockParseException() {
    }

    public ShutterStockParseException(String message) {
        super(message);
    }

    public ShutterStockParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
