package com.praktyki.backend.business.entities.dates;

import java.time.LocalDate;

public class QuarterlyDateScheduleCalculator implements DateScheduleCalculator {
    @Override
    public DateSchedule calculate(LocalDate startDate) {
        return new QuarterlyDateSchedule(startDate);
    }

    private static class QuarterlyDateSchedule extends BaseDateSchedule {

        public QuarterlyDateSchedule(LocalDate startDate) {
            super(startDate);
        }

        @Override
        public LocalDate getDateFor(int installmentIndex) {
            return getStartDate().plusMonths((installmentIndex - 1) * 3L);
        }
    }
}
