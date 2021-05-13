package com.praktyki.backend.web.validation;

import com.praktyki.backend.business.services.InstallmentScheduleService;
import com.praktyki.backend.business.value.ScheduleConfiguration;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidCommissionRateValidator implements ConstraintValidator<ValidCommissionRate, Double> {

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        return value >= InstallmentScheduleService.MIN_COMMISSION_RATE
                && value <= InstallmentScheduleService.maxCommissionRate;
    }
}
