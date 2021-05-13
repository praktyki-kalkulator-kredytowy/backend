package com.praktyki.backend.app.configuration;

import com.praktyki.backend.configuration.ConfigurationGroupKey;

public enum ConfigurationGroupKeys implements ConfigurationGroupKey {

    DEFAULT {
        @Override
        public String getDescription() {
            return "Default group for configuration values";
        }
    };


    @Override
    public String getKey() {
        return this.name();
    }
}
