package com.praktyki.backend.business.entities.dates;

import com.praktyki.backend.app.configuration.ConfigurationKeys;
import com.praktyki.backend.configuration.Configuration;

import java.time.Period;

public class InsurancePremiumDateCalculator extends BaseDateScheduleCalculator {

    private final Configuration mConfiguration;

    public InsurancePremiumDateCalculator(Configuration configuration) {
        mConfiguration = configuration;
    }

    @Override
    public Period getPeriod() {
        return Period.ofMonths( (int) Long.parseLong(mConfiguration.get(ConfigurationKeys.MONTH_FRAME)));
    }
}
