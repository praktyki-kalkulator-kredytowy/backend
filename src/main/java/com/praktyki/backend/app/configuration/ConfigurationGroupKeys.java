package com.praktyki.backend.app.configuration;

import com.praktyki.backend.configuration.ConfigurationGroupKey;

public enum ConfigurationGroupKeys implements ConfigurationGroupKey {

    DEFAULT {
        @Override
        public String getDisplayName() {
            return "Configuration";
        }

        @Override
        public String getDescription() {
            return "Default group for configuration values";
        }
    },

    INSURANCE_GROUPS {
        @Override
        public String getDisplayName() {
            return "Insurance rates based on age";
        }

        @Override
        public String getDescription() {
            return super.getDescription() + ", entries in format of age - rate";
        }
    };


    @Override
    public String getKey() {
        return this.name();
    }
}
