package com.praktyki.backend.configuration;

public interface ConfigurationGroupKey {

    String getKey();

    String getDisplayName();

    default String getDescription() { return getDisplayName(); };

    ConfigurationKey createKey(String key, String value);

}
