package com.praktyki.backend.web.requestModels;

import com.praktyki.backend.services.schedule.InstallmentType;
import com.praktyki.backend.web.validation.ValidInstallmentType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.Date;

public class ScheduleConfigurationModel {

    @Positive(message = "Capital must be positive")
    public double capital;

    @NotBlank
    @ValidInstallmentType
    public String installmentType;

    @Min(value = 2, message = "There must be at least 2 installments")
    public int installmentAmount;

    @Positive(message = "Interest rate must be positive")
    public double interestRate;

    @NotNull
    public Date withdrawalDate;
    
}
