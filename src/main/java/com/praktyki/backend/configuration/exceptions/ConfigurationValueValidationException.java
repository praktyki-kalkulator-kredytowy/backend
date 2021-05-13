package com.praktyki.backend.configuration.exceptions;

import com.praktyki.backend.configuration.ConfigurationKey;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;

import javax.validation.ConstraintViolationException;

public class ConfigurationValueValidationException extends ConfigurationException {

    public ConfigurationValueValidationException(String message, String rejectedValue, ConfigurationKey key) {
        super("Invalid value for key " + key.getName() + ": " + rejectedValue + " - " + message);
    }

}
