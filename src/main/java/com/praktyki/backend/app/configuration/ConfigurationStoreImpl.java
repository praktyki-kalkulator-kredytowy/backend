package com.praktyki.backend.app.configuration;

import com.praktyki.backend.configuration.Configuration;
import com.praktyki.backend.configuration.ConfigurationStore;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationStoreImpl implements ConfigurationStore {
    @Override
    public Configuration getConfiguration(Class<?> clazz) {
        return null;
    }
}
