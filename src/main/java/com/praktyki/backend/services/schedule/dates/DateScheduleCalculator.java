package com.praktyki.backend.services.schedule.dates;

import java.time.LocalDate;

@FunctionalInterface
public interface DateScheduleCalculator {
    DateSchedule calculate(LocalDate withdrawalDate);
}