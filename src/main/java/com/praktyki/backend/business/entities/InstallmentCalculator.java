package com.praktyki.backend.business.entities;

import java.time.LocalDate;

@FunctionalInterface
public interface InstallmentCalculator {
    Installment calculate(Installment previousInstallment, ScheduleConfiguration scheduleConfiguration, LocalDate installmentDate);
}
