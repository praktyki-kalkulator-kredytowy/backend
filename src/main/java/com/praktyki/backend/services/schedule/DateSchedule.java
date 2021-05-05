package com.praktyki.backend.services.schedule;

import java.time.LocalDate;

@FunctionalInterface
public interface DateSchedule {
    LocalDate getDateFor(int installmentIndex);
}
