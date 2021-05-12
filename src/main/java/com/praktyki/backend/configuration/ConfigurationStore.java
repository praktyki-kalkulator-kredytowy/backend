package com.praktyki.backend.configuration;

public interface ConfigurationStore {
    Configuration getConfiguration(Class<?> clazz);
}
