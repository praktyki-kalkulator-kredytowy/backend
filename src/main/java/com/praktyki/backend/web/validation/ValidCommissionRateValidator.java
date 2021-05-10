package com.praktyki.backend.web.validation;

import com.praktyki.backend.business.value.ScheduleConfiguration;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidCommissionRateValidator implements ConstraintValidator<ValidInterestRate, Double> {

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        return value >= ScheduleConfiguration.MIN_COMMISSION_RATE
                && value <= ScheduleConfiguration.MAX_COMMISSION_RATE;
    }
}
