package com.praktyki.backend.business.services.schedule.dates;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

public class MonthlyDateCalculator implements DateScheduleCalculator {

    @Override
    public DateSchedule calculate(LocalDate withdrawalDate) {
        return new DateScheduleImpl(withdrawalDate);
    }

    private static class DateScheduleImpl implements DateSchedule {
        private LocalDate withdrawalDate;

        public DateScheduleImpl(LocalDate withdrawalDate) {
            this.withdrawalDate = withdrawalDate;
        }

        @Override
        public LocalDate getDateFor(int installmentIndex) {
            return withdrawalDate.plusMonths(installmentIndex);
        }
    }
}
