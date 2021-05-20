package com.praktyki.backend.app.configuration.exceptions;

import com.praktyki.backend.configuration.ConfigurationKey;

public class ConfigurationValueValidationException extends ConfigurationException {

    public ConfigurationValueValidationException(String message, String rejectedValue, ConfigurationKey key) {
        super("Invalid value for key " + key.getKey() + ": " + rejectedValue + " - " + message);
    }

}
