package com.praktyki.backend.app.configuration;

import com.praktyki.backend.app.data.entities.ConfigurationEntryEntity;
import com.praktyki.backend.app.data.repositories.ConfigurationRepository;
import com.praktyki.backend.app.configuration.exceptions.ConfigurationValueValidationException;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ConfigurationGroupImpl implements ConfigurationGroup {

    private ConfigurationGroupKey mGroupKey;
    private ConfigurationRepository mConfigurationRepository;

    public ConfigurationGroupImpl(ConfigurationGroupKey groupKey, ConfigurationRepository configurationRepository) {
        mGroupKey = groupKey;
        mConfigurationRepository = configurationRepository;
    }

    @Override
    public ConfigurationGroupKey getGroupKey() {
        return mGroupKey;
    }

    @Override
    public Collection<ConfigurationEntry> getEntries() {
        return StreamSupport
                .stream(
                        mConfigurationRepository.findEntriesForGroup(mGroupKey.getKey()).spliterator(),
                        false
                )
                .map(this::mapToEntry)
                .collect(Collectors.toList());
    }

    @Override
    public String get(ConfigurationKey key) {
        return mConfigurationRepository.find(mGroupKey.getKey(), key.getKey())
                .map(e -> e.value)
                .orElseGet(key::getDefaultValue);
    }

    @Override
    public ConfigurationEntry getEntry(ConfigurationKey key) {
        return mConfigurationRepository.find(mGroupKey.getKey(), key.getKey())
                .map(this::mapToEntry)
                .orElseGet(() -> new ConfigurationEntryImpl(key, key.getDefaultValue()));
    }

    @Override
    public ConfigurationGroup save(ConfigurationKey key, String value) throws ConfigurationValueValidationException {
        key.validate(value);

        mConfigurationRepository.removeKey(mGroupKey.getKey(), key.getKey());

        mConfigurationRepository.save(new ConfigurationEntryEntity(0, key.getKey(), value, mGroupKey.getKey()));
        return this;
    }

    @Override
    public ConfigurationGroup remove(ConfigurationKey key) {
        mConfigurationRepository.removeKey(mGroupKey.getKey(), key.getKey());
        return this;
    }

    private ConfigurationEntry mapToEntry(ConfigurationEntryEntity entity) {
        return new ConfigurationEntryImpl(mGroupKey.createKey(entity.key), entity.value);
    }
}
