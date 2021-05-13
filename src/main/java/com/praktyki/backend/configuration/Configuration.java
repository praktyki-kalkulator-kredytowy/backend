package com.praktyki.backend.configuration;


import com.praktyki.backend.configuration.exceptions.ConfigurationValueValidationException;

import java.util.Collection;

public interface Configuration {

    String get(ConfigurationKey key);

    Configuration save(ConfigurationKey key, String value) throws ConfigurationValueValidationException;

    Collection<ConfigurationGroup> getGroups();

    ConfigurationGroup getGroup(ConfigurationGroupKey key);

}