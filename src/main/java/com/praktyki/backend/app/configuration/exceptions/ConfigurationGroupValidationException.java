package com.praktyki.backend.app.configuration.exceptions;

public class ConfigurationGroupValidationException extends ConfigurationException{

    public ConfigurationGroupValidationException(String groupName) {
        super("Invalid group: " + groupName);
    }
}
