package com.praktyki.backend.app.configuration;

import com.praktyki.backend.app.data.repositories.ConfigurationRepository;
import com.praktyki.backend.configuration.Configuration;
import com.praktyki.backend.configuration.ConfigurationGroup;
import com.praktyki.backend.configuration.ConfigurationGroupKey;
import com.praktyki.backend.configuration.ConfigurationKey;
import com.praktyki.backend.configuration.exceptions.ConfigurationValueValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class ConfigurationImpl implements Configuration {

    @Autowired
    private ConfigurationRepository mConfigurationRepository;

    private final Map<ConfigurationGroupKey, ConfigurationGroup> mGroups = new HashMap<>();

    @PostConstruct
    private void init() {
        Arrays.stream(ConfigurationGroupKeys.values()).forEach(key -> mGroups.put(
                key,
                new ConfigurationGroupImpl(key, mConfigurationRepository)
        ));
    }



    @Override
    public String get(ConfigurationKey key) {
        return mGroups.get(ConfigurationGroupKeys.DEFAULT).get(key);
    }

    @Override
    public Configuration save(ConfigurationKey key, String value) throws ConfigurationValueValidationException {
        mGroups.get(ConfigurationGroupKeys.DEFAULT).save(key, value);
        return this;
    }

    @Override
    public Collection<ConfigurationGroup> getGroups() {
        return new ArrayList<>(mGroups.values());
    }

    @Override
    public ConfigurationGroup getGroup(ConfigurationGroupKey key) {
        return mGroups.get(key);
    }
}
