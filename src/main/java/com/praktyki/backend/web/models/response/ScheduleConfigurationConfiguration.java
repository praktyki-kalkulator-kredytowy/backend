package com.praktyki.backend.web.models.response;

import com.praktyki.backend.app.configuration.ConfigurationKeys;
import com.praktyki.backend.configuration.Configuration;

public class ScheduleConfigurationConfiguration {

    public double defaultCommissionRate;
    public double minInterestRate;
    public double maxInterestRate;
    public double minCommissionRate;
    public double maxCommissionRate;


    public ScheduleConfigurationConfiguration(Configuration configuration) {
        this.defaultCommissionRate = Double.parseDouble(configuration.get(ConfigurationKeys.DEFAULT_COMMISSION_RATE));
        this.minInterestRate = Double.parseDouble(configuration.get(ConfigurationKeys.MIN_INTEREST_RATE));
        this.maxInterestRate = Double.parseDouble(configuration.get(ConfigurationKeys.MAX_INTEREST_RATE));
        this.minCommissionRate = Double.parseDouble(configuration.get(ConfigurationKeys.MIN_COMMISSION_RATE));
        this.maxCommissionRate = Double.parseDouble(configuration.get(ConfigurationKeys.MAX_COMMISSION_RATE));

    }
}
