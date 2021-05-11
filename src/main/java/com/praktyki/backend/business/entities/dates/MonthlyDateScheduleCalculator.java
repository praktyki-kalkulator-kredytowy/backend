package com.praktyki.backend.business.entities.dates;

import java.time.LocalDate;

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
        public LocalDate getDateFor(int installmentIndex) {
            return getStartDate().plusMonths(installmentIndex - 1);
        }
    }

}
