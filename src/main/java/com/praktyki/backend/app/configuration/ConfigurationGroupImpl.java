package com.praktyki.backend.app.configuration;

import com.praktyki.backend.app.data.entities.ConfigurationEntryEntity;
import com.praktyki.backend.app.data.repositories.ConfigurationRepository;
import com.praktyki.backend.configuration.ConfigurationEntry;
import com.praktyki.backend.configuration.ConfigurationGroup;
import com.praktyki.backend.configuration.ConfigurationGroupKey;
import com.praktyki.backend.configuration.ConfigurationKey;
import com.praktyki.backend.configuration.exceptions.ConfigurationValueValidationException;

import java.util.*;

public class ConfigurationGroupImpl implements ConfigurationGroup {

    private ConfigurationGroupKey mGroupKey;
    private ConfigurationRepository mConfigurationRepository;
    private Map<ConfigurationKey, ConfigurationEntry> mEntries = new HashMap<>();

    public ConfigurationGroupImpl(ConfigurationGroupKey groupKey, ConfigurationRepository configurationRepository) {
        mGroupKey = groupKey;
        mConfigurationRepository = configurationRepository;

        for(ConfigurationEntryEntity entity : configurationRepository.findEntriesForGroup(mGroupKey.getKey())) {
            ConfigurationEntry entry = mapToEntry(entity);
            mEntries.put(entry.getKey(), entry);
        }

    }

    @Override
    public ConfigurationGroupKey getGroupKey() {
        return mGroupKey;
    }

    @Override
    public Collection<ConfigurationEntry> getEntries() {
        return new ArrayList<>(mEntries.values());
    }

    @Override
    public String get(ConfigurationKey key) {
        if(mEntries.containsKey(key))
            return mEntries.get(key).getValue();

        return key.getDefaultValue();
    }

    @Override
    public ConfigurationGroup save(ConfigurationKey key, String value) throws ConfigurationValueValidationException {
        key.validate(value);

        mConfigurationRepository.save(new ConfigurationEntryEntity(0, key.getName(), value, mGroupKey.getKey()));
        mEntries.put(key, new ConfigurationEntryImpl(key, value));
        return this;
    }

    private static ConfigurationEntry mapToEntry(ConfigurationEntryEntity entity) {
        return new ConfigurationEntryImpl(ConfigurationKeys.valueOf(entity.key), entity.value);
    }
}
