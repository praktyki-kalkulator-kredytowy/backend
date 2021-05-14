package com.praktyki.backend.business.entities.dates;

import java.time.LocalDate;

public class ConfiguredDateScheduleCalculator implements DateScheduleCalculator {

    private long monthFrame;

    public void setMonthFrame(long monthFrame) {
        this.monthFrame = monthFrame;
    }

    public ConfiguredDateScheduleCalculator() {
    }

    @Override
    public DateSchedule calculate(LocalDate startDate) {
        return new CustomScheduleImpl(startDate,monthFrame);
    }

    private static class CustomScheduleImpl extends BaseDateSchedule {

        private final long monthFrame;

        public CustomScheduleImpl(LocalDate startDate, long monthFrame) {
            super(startDate);
            this.monthFrame = monthFrame;
        }


        @Override
        public LocalDate getDateFor(int installmentIndex) {
            return getStartDate().plusMonths((installmentIndex - 1) * monthFrame) ;
        }
    }
}
