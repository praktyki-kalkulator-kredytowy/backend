package com.praktyki.backend.business.entities;

import com.praktyki.backend.business.value.Installment;
import com.praktyki.backend.business.value.ScheduleConfiguration;

import java.time.LocalDate;

@FunctionalInterface
public interface InstallmentCalculator {
    Installment calculate(Installment previousInstallment, ScheduleConfiguration scheduleConfiguration, LocalDate installmentDate);
}
