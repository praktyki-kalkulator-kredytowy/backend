package com.praktyki.backend.app.configuration;

import com.praktyki.backend.app.data.repositories.ConfigurationRepository;
import com.praktyki.backend.app.configuration.exceptions.ConfigurationValueValidationException;
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
    public ConfigurationEntry getEntry(ConfigurationKey key) {
        return mGroups.get(ConfigurationGroupKeys.DEFAULT).getEntry(key);
    }

    @Override
    public Configuration save(ConfigurationKey key, String value) throws ConfigurationValueValidationException {
        mGroups.get(ConfigurationGroupKeys.DEFAULT).save(key, value);
        return this;
    }

    @Override
    public ConfigurationGroupKey getGroupKey() {
        return mGroups.get(ConfigurationGroupKeys.DEFAULT).getGroupKey();
    }

    @Override
    public Collection<ConfigurationEntry> getEntries() {
        return mGroups.get(ConfigurationGroupKeys.DEFAULT).getEntries();
    }

    @Override
    public ConfigurationGroup remove(ConfigurationKey key) {
        mGroups.get(ConfigurationGroupKeys.DEFAULT).remove(key);
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
