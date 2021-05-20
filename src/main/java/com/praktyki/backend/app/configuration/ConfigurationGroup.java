package com.praktyki.backend.app.configuration;

import com.praktyki.backend.app.configuration.exceptions.ConfigurationValueValidationException;

import java.util.Collection;

public interface ConfigurationGroup {

    ConfigurationGroupKey getGroupKey();

    Collection<ConfigurationEntry> getEntries();

    String get(ConfigurationKey key);

    ConfigurationEntry getEntry(ConfigurationKey key);

    ConfigurationGroup save(ConfigurationKey key, String value) throws ConfigurationValueValidationException;

    ConfigurationGroup remove(ConfigurationKey key);

}
