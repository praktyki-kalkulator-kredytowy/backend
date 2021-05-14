package com.praktyki.backend.business.entities;

import com.praktyki.backend.app.configuration.ConfigurationGroupKeys;
import com.praktyki.backend.business.services.exceptions.NoInsuranceRateForAgeException;
import com.praktyki.backend.configuration.Configuration;
import com.praktyki.backend.configuration.ConfigurationEntry;
import com.praktyki.backend.configuration.ConfigurationKey;

import java.util.Collection;

public class InstallmentRateConfigurationImpl implements InstallmentRateConfiguration {

    private Configuration mConfiguration;

    public InstallmentRateConfigurationImpl(Configuration configuration) {
        mConfiguration = configuration;
    }

    @Override
    public double getRateForAge(double age) throws NoInsuranceRateForAgeException {
        Collection<ConfigurationEntry> entries = mConfiguration
                .getGroup(ConfigurationGroupKeys.INSURANCE_GROUPS)
                .getEntries();

        int key = entries.stream()
                .map(ConfigurationEntry::getKey)
                .map(ConfigurationKey::getKey)
                .map(Integer::parseInt)
                .filter(k -> k <= age)
                .max(Integer::compare)
                .orElseThrow(() -> new NoInsuranceRateForAgeException(age));

        return Double.parseDouble(
                mConfiguration
                        .getGroup(ConfigurationGroupKeys.INSURANCE_GROUPS)
                        .get(ConfigurationGroupKeys.INSURANCE_GROUPS.createKey(String.valueOf(key)))
        );
    }
}
