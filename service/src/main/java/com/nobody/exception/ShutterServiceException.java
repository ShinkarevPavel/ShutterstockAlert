package com.nobody.exception;

public class ShutterServiceException extends RuntimeException{
    public ShutterServiceException() {
        super();
    }

    public ShutterServiceException(String message) {
        super(message);
    }

    public ShutterServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
