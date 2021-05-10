package com.praktyki.backend.web.validation;

import com.praktyki.backend.business.entities.InstallmentType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidInstallmentTypeValidator implements ConstraintValidator<ValidInstallmentType, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            InstallmentType.valueOf(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
