package com.praktyki.backend.web.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidInterestRateValidator implements ConstraintValidator<ValidInterestRate, Double> {

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        return value >= 0.01 && value <= 1;
    }
}
