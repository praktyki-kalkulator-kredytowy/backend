package com.praktyki.backend.configuration;

import com.praktyki.backend.configuration.exceptions.ConfigurationValueValidationException;

public interface ConfigurationKey {

    String getName();

    String getDefaultValue();

    String getDescription();

    void validate(String value) throws ConfigurationValueValidationException;

}
