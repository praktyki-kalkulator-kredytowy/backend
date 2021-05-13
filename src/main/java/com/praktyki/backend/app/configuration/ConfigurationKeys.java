package com.praktyki.backend.app.configuration;

import com.praktyki.backend.business.services.InsuranceService;
import com.praktyki.backend.configuration.ConfigurationKey;
import com.praktyki.backend.configuration.exceptions.ConfigurationValueValidationException;

public enum ConfigurationKeys implements ConfigurationKey {
    MIN_INSURANCE_PREMIUM_VALUE {
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
            int v;
            try {
                v = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                throw new ConfigurationValueValidationException("Must be a number", value, this);
            }

            if(v < 0)
                throw new ConfigurationValueValidationException("Must be positive or zero", value, this);
        }
    };

    @Override
    public String getName() {
        return this.name();
    }
}
