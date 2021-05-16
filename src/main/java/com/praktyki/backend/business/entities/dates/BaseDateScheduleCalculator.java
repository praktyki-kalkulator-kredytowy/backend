package com.praktyki.backend.business.entities.dates;

import java.time.LocalDate;
import java.time.temporal.TemporalUnit;

public abstract class BaseDateScheduleCalculator implements DateScheduleCalculator {

    public abstract long getInterval();

    public abstract TemporalUnit getUnit();

    @Override
    public DateSchedule calculate(LocalDate startDate) {
        return new BaseDateSchedule(startDate) {
            @Override
            public LocalDate getDateFor(int installmentIndex) {
                return startDate.plus(getInterval() * (installmentIndex - 1), getUnit());
            }
        };
    }
}
