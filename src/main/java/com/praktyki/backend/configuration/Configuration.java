package com.praktyki.backend.configuration;


import com.praktyki.backend.configuration.exceptions.ConfigurationValueValidationException;

import java.util.Collection;

public interface Configuration extends ConfigurationGroup{

    Collection<ConfigurationGroup> getGroups();

    ConfigurationGroup getGroup(ConfigurationGroupKey key);

}