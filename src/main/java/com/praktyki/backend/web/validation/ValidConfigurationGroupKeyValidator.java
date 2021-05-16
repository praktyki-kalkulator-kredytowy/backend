package com.praktyki.backend.web.validation;

import com.praktyki.backend.app.configuration.ConfigurationGroupKeys;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidConfigurationGroupKeyValidator implements ConstraintValidator<ValidConfigurationGroupKey, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try{
            ConfigurationGroupKeys.valueOf(value);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
