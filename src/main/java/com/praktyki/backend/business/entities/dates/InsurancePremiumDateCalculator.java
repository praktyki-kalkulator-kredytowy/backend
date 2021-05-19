package com.praktyki.backend.business.entities.dates;

import com.praktyki.backend.app.configuration.ConfigurationKeys;
import com.praktyki.backend.app.configuration.Configuration;

import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

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
