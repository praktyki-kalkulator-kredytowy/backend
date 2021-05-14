package com.praktyki.backend.configuration;

import com.praktyki.backend.configuration.exceptions.ConfigurationValueValidationException;

public interface ConfigurationKey {

    String getName();

    String getDisplayName();

    String getDefaultValue();

    default String getDescription() { return getDisplayName(); };

    void validate(String value) throws ConfigurationValueValidationException;

    @Override
    boolean equals(Object obj);

    @Override
    int hashCode();

}
