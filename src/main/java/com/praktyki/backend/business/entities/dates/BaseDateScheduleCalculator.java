package com.praktyki.backend.business.entities.dates;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAmount;

public abstract class BaseDateScheduleCalculator implements DateScheduleCalculator {

    public abstract Period getPeriod();

    @Override
    public DateSchedule calculate(LocalDate startDate) {
        return new BaseDateSchedule(startDate) {
            @Override
            TemporalAmount getAmountToAdd(int index) {
                return getPeriod().multipliedBy(index - 1);
            }
        };
    }
}
