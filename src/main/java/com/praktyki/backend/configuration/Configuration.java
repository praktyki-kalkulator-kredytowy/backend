package com.praktyki.backend.configuration;


import java.util.Collection;

public interface Configuration extends ConfigurationGroup{

    Collection<ConfigurationGroup> getGroups();

    ConfigurationGroup getGroup(ConfigurationGroupKey key);

}