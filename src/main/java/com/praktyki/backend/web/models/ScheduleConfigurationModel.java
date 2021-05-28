package com.praktyki.backend.web.models;

import com.praktyki.backend.web.validation.ValidCommissionRate;
import com.praktyki.backend.web.validation.ValidInstallmentType;
import com.praktyki.backend.web.validation.ValidInterestRate;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Objects;

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
    public LocalDate withdrawalDate;

    @ValidCommissionRate
    public double commissionRate;

    @Min(value = 0, message = "Age must be positive")
    @Max(value = 150, message = "Maximum age value is 150")
    public int age;

    public boolean insurance;

    public ScheduleConfigurationModel() {
    }

    public ScheduleConfigurationModel(
            double capital,
            String installmentType,
            int installmentAmount,
            double interestRate,
            LocalDate withdrawalDate,
            double commissionRate,
            int age,
            boolean insurance
    ) {
        this.capital = capital;
        this.installmentType = installmentType;
        this.installmentAmount = installmentAmount;
        this.interestRate = interestRate;
        this.withdrawalDate = withdrawalDate;
        this.commissionRate = commissionRate;
        this.age = age;
        this.insurance = insurance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleConfigurationModel that = (ScheduleConfigurationModel) o;
        return Double.compare(that.capital, capital) == 0
                && installmentAmount == that.installmentAmount
                && Double.compare(that.interestRate, interestRate) == 0
                && Double.compare(that.commissionRate, commissionRate) == 0
                && age == that.age && insurance == that.insurance
                && installmentType.equals(that.installmentType)
                && withdrawalDate.equals(that.withdrawalDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                capital,
                installmentType,
                installmentAmount,
                interestRate,
                withdrawalDate,
                commissionRate,
                age,
                insurance
        );
    }

    @Override
    public String toString() {
        return "ScheduleConfigurationModel{" +
                "capital =" + capital +
                ", installmentType ='" + installmentType + '\'' +
                ", installmentAmount =" + installmentAmount +
                ", interestRate =" + interestRate +
                ", withdrawalDate =" + withdrawalDate +
                ", commissionRate =" + commissionRate +
                ", age =" + age +
                ", insurance =" + insurance +
                '}';
    }
}
