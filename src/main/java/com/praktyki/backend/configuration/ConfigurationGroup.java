package com.praktyki.backend.configuration;

import java.util.Collection;

public interface ConfigurationGroup {

    String getName();

    Collection<ConfigurationEntry> getEntries();

    String get(String key);

    ConfigurationGroup save(String key, String value);

    ConfigurationGroup require(String key, String defaultValue, String description);

}
