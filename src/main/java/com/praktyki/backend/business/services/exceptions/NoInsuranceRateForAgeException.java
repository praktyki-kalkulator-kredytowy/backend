package com.praktyki.backend.business.services.exceptions;

public class NoInsuranceRateForAgeException extends Exception {

    public NoInsuranceRateForAgeException(double age) {
        super("Age " + age + " does not match any insurance rate");
    }

}
