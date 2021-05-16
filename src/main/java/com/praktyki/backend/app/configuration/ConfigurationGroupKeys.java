package com.praktyki.backend.app.configuration;

import com.praktyki.backend.configuration.ConfigurationGroupKey;
import com.praktyki.backend.configuration.ConfigurationKey;
import com.praktyki.backend.configuration.exceptions.ConfigurationValueValidationException;

public enum ConfigurationGroupKeys implements ConfigurationGroupKey {

    DEFAULT {

        @Override
        public ConfigurationKey createKey(String key) {

            try {
                return ConfigurationKeys.valueOf(key);
            }
            catch (IllegalArgumentException ex) { }

            return new BaseConfigurationKey() {
                @Override
                public String getKey() {
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
        public ConfigurationKey createKey(String key) {
            return new BaseConfigurationKey() {
                @Override
                public String getKey() {
                    return key;
                }

                @Override
                public String getDisplayName() {
                    return "For " + key + "years old and less";
                }

                @Override
                public String getDefaultValue() {
                    return "0";
                }

                @Override
                public void validate(String value) throws ConfigurationValueValidationException {
                    try {
                        int v = Integer.parseInt(key);

                        if(v < 0) throw new ConfigurationValueValidationException(
                                "keys in " + INSURANCE_GROUPS.getDisplayName() + " group must be positive whole numbers " +
                                        "representing age",
                                getKey(),
                                this
                        );
                    } catch (NumberFormatException e) {
                        throw new ConfigurationValueValidationException(
                                "keys in " + INSURANCE_GROUPS.getDisplayName() + " group must be positive whole numbers " +
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
