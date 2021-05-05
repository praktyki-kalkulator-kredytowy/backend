package com.praktyki.backend.web.exception;

public class ConfigurationNotFound extends Exception {

    public ConfigurationNotFound() {
        super();
    }

    public ConfigurationNotFound(String message) {
        super(message);
    }

    public ConfigurationNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigurationNotFound(Throwable cause) {
        super(cause);
    }

    protected ConfigurationNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
