package com.praktyki.backend.web.models.response;

import com.praktyki.backend.app.configuration.ConfigurationKeys;
import com.praktyki.backend.configuration.Configuration;

public class ScheduleConfigurationConfiguration {

    public double defaultCommissionRate;

    public ScheduleConfigurationConfiguration(Configuration configuration) {
        this.defaultCommissionRate = Double.parseDouble(configuration.get(ConfigurationKeys.DEFAULT_COMMISSION_RATE));
    }
}
