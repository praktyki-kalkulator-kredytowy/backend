package com.praktyki.backend.business.entities.dates;

import java.time.LocalDate;

@FunctionalInterface
public interface DateScheduleCalculator {
    DateSchedule calculate(LocalDate startDate);
}
