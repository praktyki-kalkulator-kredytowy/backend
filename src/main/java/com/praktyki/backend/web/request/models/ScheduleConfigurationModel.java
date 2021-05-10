package com.praktyki.backend.web.request.models;

import com.praktyki.backend.web.validation.ValidInstallmentType;
import com.praktyki.backend.web.validation.ValidInterestRate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

public class ScheduleConfigurationModel {

    @Positive(message = "Capital must be positive")
    public double capital;

    @NotBlank(message = "Please provide an installation type")
    @ValidInstallmentType
    public String installmentType;

    @Min(value = 2, message = "There must be at least 2 installments")
    public int installmentAmount;

    @ValidInterestRate
    public double interestRate;

    @NotNull(message = "Please specify a withdrawal date")
    public Date withdrawalDate;
    
}
