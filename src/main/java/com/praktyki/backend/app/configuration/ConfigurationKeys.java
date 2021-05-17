package com.praktyki.backend.app.configuration;

import com.praktyki.backend.app.configuration.exceptions.ConfigurationValueValidationException;

public enum ConfigurationKeys implements ConfigurationKey {
    MIN_PREMIUM_VALUE {
        @Override
        public String getDisplayName() {
            return "Minimal premium";
        }

        @Override
        public String getDefaultValue() {
            return "10";
        }

        @Override
        public String getDescription() {
            return "Minimal amount for a insurance premium";
        }

        @Override
        public void validate(String value) throws ConfigurationValueValidationException {
            ConfigurationValidationUtils.number(this, value);
        }
    },

    MIN_COMMISSION_AMOUNT {
        @Override
        public String getDisplayName() {
            return "Minimal commission amount";
        }

        @Override
        public String getDefaultValue() {
            return "50";
        }


        @Override
        public void validate(String value) throws ConfigurationValueValidationException {
            ConfigurationValidationUtils.min(this,value, 0);
        }
    },

    MIN_INTEREST_RATE {
        @Override
        public String getDisplayName() {
            return "Minimal interest rate";
        }

        @Override
        public String getDefaultValue() {
            return "0.00";
        }

        @Override
        public void validate(String value) throws ConfigurationValueValidationException {
            ConfigurationValidationUtils.min(this, value, 0);
        }
    },

    MAX_INTEREST_RATE {
        @Override
        public String getDisplayName() {
            return "Maximal interest rate";
        }

        @Override
        public String getDefaultValue() {
            return "1";
        }

        @Override
        public void validate(String value) throws ConfigurationValueValidationException {
            ConfigurationValidationUtils.within(this, value,0,1);
        }
    },

    MIN_COMMISSION_RATE {
        @Override
        public String getDisplayName() {
            return "Minimal commission rate";
        }

        @Override
        public String getDefaultValue() {
            return "0";
        }

        @Override
        public void validate(String value) throws ConfigurationValueValidationException {
            ConfigurationValidationUtils.number(this, value);
        }
    },

    MAX_COMMISSION_RATE {
        @Override
        public String getDisplayName() {
            return "Maximal commission rate";
        }

        @Override
        public String getDefaultValue() {
            return "0.2";
        }

        @Override
        public void validate(String value) throws ConfigurationValueValidationException {
            ConfigurationValidationUtils.within(this, value,0,1);
        }
    },

    DEFAULT_COMMISSION_RATE {
        @Override
        public String getDisplayName() {
            return "Default value for commission rate";
        }

        @Override
        public String getDefaultValue() {
            return "0.1";
        }

        @Override
        public void validate(String value) throws ConfigurationValueValidationException {
            ConfigurationValidationUtils.within(this, value, 0, 1);
        }
    },

    MONTH_FRAME {
        @Override
        public String getDisplayName() {
            return "Insurance premium frequency";
        }

        @Override
        public String getDescription() {
            return "Insurance premium frequency in months";
        }

        @Override
        public String getDefaultValue() {
            return "3";
        }

        @Override
        public void validate(String value) throws ConfigurationValueValidationException {
            ConfigurationValidationUtils.min(this, value, 0);
            ConfigurationValidationUtils.wholeNumber(this, value);
        }
    };



    @Override
    public String getKey() {
        return this.name();
    }
}
