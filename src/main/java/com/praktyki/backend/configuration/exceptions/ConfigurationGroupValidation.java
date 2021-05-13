package com.praktyki.backend.configuration.exceptions;

public class ConfigurationGroupValidation extends ConfigurationException{

    public ConfigurationGroupValidation() {
        super("Invalid group name");
    }
}
