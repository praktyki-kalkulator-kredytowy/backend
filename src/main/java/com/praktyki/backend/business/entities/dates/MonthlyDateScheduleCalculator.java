package com.praktyki.backend.business.entities.dates;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;

public class MonthlyDateScheduleCalculator implements DateScheduleCalculator {

    @Override
    public DateSchedule calculate(LocalDate startDate) {
        return new DateScheduleImpl(startDate);
    }

    private static class DateScheduleImpl extends BaseDateSchedule {

        public DateScheduleImpl(LocalDate startDate) {
            super(startDate);
        }

        @Override
        TemporalAmount getAmountToAdd(int index) {
            return Period.ofMonths(index - 1);
        }
    }

}
