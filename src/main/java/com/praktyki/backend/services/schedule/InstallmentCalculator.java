package com.praktyki.backend.services.schedule;

import java.time.LocalDate;

@FunctionalInterface
public interface InstallmentCalculator {
    Installment calculate(Installment previousInstallment, ScheduleConfiguration scheduleConfiguration, LocalDate installmentDate);
}
