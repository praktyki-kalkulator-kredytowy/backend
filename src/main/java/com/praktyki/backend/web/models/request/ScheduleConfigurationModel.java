package com.praktyki.backend.web.models.request;

import com.praktyki.backend.web.validation.ValidCommissionRate;
import com.praktyki.backend.web.validation.ValidInstallmentType;
import com.praktyki.backend.web.validation.ValidInterestRate;

import javax.validation.constraints.*;
import java.util.Date;

public class ScheduleConfigurationModel {

    @Positive(message = "Capital must be positive")
    public double capital;

    @NotBlank(message = "Please provide an installation type")
    @ValidInstallmentType
    public String installmentType;

    @Min(value = 2, message = "Minimum amount of installments not reached")
    @Max(value = 360, message = "The maximum installment exceeded")
    public int installmentAmount;

    @ValidInterestRate
    public double interestRate;

    @NotNull(message = "Please specify a withdrawal date")
    public Date withdrawalDate;

    @ValidCommissionRate
    public double commissionRate;

    @Min(value = 0, message = "Age must be positive")
    @Max(value = 150, message = "Maximum age value is 150")
    public int age;

    public boolean insurance;
}
