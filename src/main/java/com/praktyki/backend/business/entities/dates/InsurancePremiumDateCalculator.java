package com.praktyki.backend.business.entities.dates;

import com.praktyki.backend.app.configuration.ConfigurationKeys;
import com.praktyki.backend.app.configuration.Configuration;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

public class InsurancePremiumDateCalculator extends BaseDateScheduleCalculator {

    private final Configuration mConfiguration;

    public InsurancePremiumDateCalculator(Configuration configuration) {
        mConfiguration = configuration;
    }

    @Override
    public long getInterval() {
        return Long.parseLong(mConfiguration.get(ConfigurationKeys.MONTH_FRAME));
    }

    @Override
    public TemporalUnit getUnit() {
        return ChronoUnit.MONTHS;
    }
}
