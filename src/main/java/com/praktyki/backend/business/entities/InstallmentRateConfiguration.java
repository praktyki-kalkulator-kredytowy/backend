package com.praktyki.backend.business.entities;

import com.praktyki.backend.business.services.exceptions.NoInsuranceRateForAgeException;

@FunctionalInterface
public interface InstallmentRateConfiguration {
    double getRateForAge(double age) throws NoInsuranceRateForAgeException;
}
