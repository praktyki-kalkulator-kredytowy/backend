package com.praktyki.backend.web.validation;

import com.praktyki.backend.app.configuration.ConfigurationKeys;
import com.praktyki.backend.app.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidCommissionRateValidator implements ConstraintValidator<ValidCommissionRate, Double> {

    @Autowired
    private Configuration mConfiguration;

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        return value >= Double.parseDouble(mConfiguration.get(ConfigurationKeys.MIN_COMMISSION_RATE))
                && value <= Double.parseDouble(mConfiguration.get(ConfigurationKeys.MAX_COMMISSION_RATE));
    }
}
