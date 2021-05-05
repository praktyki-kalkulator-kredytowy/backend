package com.praktyki.backend.services.schedule.dates;

import java.time.LocalDate;

@FunctionalInterface
public interface DateSchedule {
    LocalDate getDateFor(int installmentIndex);
}
