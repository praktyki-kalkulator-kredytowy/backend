package com.praktyki.backend.app.configuration;

public class ConfigurationEntryImpl implements ConfigurationEntry {
    private final ConfigurationKey mKey;
    private final String mValue;

    public ConfigurationEntryImpl(ConfigurationKey key, String value) {
        mKey = key;
        mValue = value;
    }

    @Override
    public ConfigurationKey getKey() {
        return mKey;
    }

    @Override
    public String getValue() {
        return mValue;
    }
}
