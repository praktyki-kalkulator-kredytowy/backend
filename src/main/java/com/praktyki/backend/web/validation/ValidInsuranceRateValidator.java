package com.praktyki.backend.web.validation;

import com.praktyki.backend.business.value.ScheduleConfiguration;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidInsuranceRateValidator implements ConstraintValidator<ValidInsuranceRate, Double> {

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        return value >= 0;
    }
}
