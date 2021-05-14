package com.praktyki.backend.configuration;

public interface ConfigurationGroupKey {

    String getKey();

    String getDisplayName();

    default String getDescription() { return getDisplayName(); };

    boolean isMutable();

    ConfigurationKey createKey(String key);

    @Override
    boolean equals(Object obj);

    @Override
    int hashCode();

}
