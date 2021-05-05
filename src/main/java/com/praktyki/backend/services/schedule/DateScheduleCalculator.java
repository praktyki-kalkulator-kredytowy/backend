package com.praktyki.backend.services.schedule;

import java.time.LocalDate;

@FunctionalInterface
public interface DateScheduleCalculator {
    DateSchedule calculate(LocalDate withdrawalDate);
}
