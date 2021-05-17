package com.praktyki.backend.app.configuration;

import com.praktyki.backend.app.configuration.exceptions.ConfigurationValueValidationException;

public interface ConfigurationKey {

    String getKey();

    String getDisplayName();

    String getDefaultValue();

    default String getDescription() { return getDisplayName(); };

    void validate(String value) throws ConfigurationValueValidationException;

    @Override
    boolean equals(Object obj);

    @Override
    int hashCode();

}
