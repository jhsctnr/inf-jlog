package com.jlog.api.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class JlogException extends RuntimeException {

    private final Map<String, String> validatioin = new HashMap<>();

    public JlogException(String message) {
        super(message);
    }

    public JlogException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();

    public void addValidation(String fieldName, String message) {
        validatioin.put(fieldName, message);
    }
}
