package com.praktyki.backend.configuration;

import java.util.Collection;

public interface Configuration {

    String get(String key);

    Configuration save(String key, String value);

    ConfigurationGroup getDefaultGroup();

    Collection<ConfigurationGroup> getGroups();

    ConfigurationGroup getGroup(String name);

    Configuration requireGroup(String name, String description);

    Configuration require(String name, String key);

}
