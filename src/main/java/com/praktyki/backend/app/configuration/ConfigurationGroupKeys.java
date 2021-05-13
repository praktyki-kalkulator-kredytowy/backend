package com.praktyki.backend.app.configuration;

import com.praktyki.backend.configuration.ConfigurationGroupKey;
import com.praktyki.backend.configuration.ConfigurationKey;
import com.praktyki.backend.configuration.exceptions.ConfigurationValueValidationException;

public enum ConfigurationGroupKeys implements ConfigurationGroupKey {

    DEFAULT {
        @Override
        public ConfigurationKey createKey(String key, String value) {

            try {
                return ConfigurationKeys.valueOf(key);
            }
            catch (IllegalArgumentException ex) { }

            return new ConfigurationKey() {
                @Override
                public String getName() {
                    return key;
                }

                @Override
                public String getDisplayName() {
                    return key;
                }

                @Override
                public String getDefaultValue() {
                    return "null";
                }

                @Override
                public void validate(String value) throws ConfigurationValueValidationException {
                    throw new ConfigurationValueValidationException(
                            "you can not add new keys  to the default group",
                            value,
                            this
                    );
                }
            };


        }

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
        public ConfigurationKey createKey(String key, String value) {
            return new ConfigurationKey() {
                @Override
                public String getName() {
                    return key;
                }

                @Override
                public String getDisplayName() {
                    return "For " + value + "years old and less";
                }

                @Override
                public String getDefaultValue() {
                    return "0";
                }

                @Override
                public void validate(String value) throws ConfigurationValueValidationException {
                    try {
                        Double.parseDouble(value);
                    } catch (NumberFormatException e) {
                        throw new ConfigurationValueValidationException(
                                "keys in " + INSURANCE_GROUPS.getDisplayName() + " group must be numbers " +
                                        "representing age",
                                getKey(),
                                this
                        );
                    }

                    ConfigurationValidationUtils.number(this, value);
                }
            };
        }

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
