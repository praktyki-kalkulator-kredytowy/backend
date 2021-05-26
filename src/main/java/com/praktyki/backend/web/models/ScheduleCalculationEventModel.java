package com.praktyki.backend.web.models;


import java.time.LocalDate;
import java.util.Objects;

public class ScheduleCalculationEventModel {
    public int id;
    public double capital;
    public String installmentType;
    public int installmentAmount;
    public double interestRate;
    public LocalDate withdrawalDate;
    public double commissionRate;
    public int age;
    public boolean insurance;
    public double capitalInstallmentSum;
    public double loanPaidOutAmount;
    public double commissionAmount;
    public double insuranceTotalAmount;
    public double loanTotalCost;
    public double aprc;
    public LocalDate calculationDate;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleCalculationEventModel that = (ScheduleCalculationEventModel) o;
        return id == that.id
                && Double.compare(that.capital, capital) == 0
                && installmentAmount == that.installmentAmount
                && Double.compare(that.interestRate, interestRate) == 0
                && Double.compare(that.commissionRate, commissionRate) == 0
                && age == that.age && insurance == that.insurance
                && Double.compare(that.capitalInstallmentSum, capitalInstallmentSum) == 0
                && Double.compare(that.loanPaidOutAmount, loanPaidOutAmount) == 0
                && Double.compare(that.commissionAmount, commissionAmount) == 0
                && Double.compare(that.insuranceTotalAmount, insuranceTotalAmount) == 0
                && Double.compare(that.loanTotalCost, loanTotalCost) == 0 && Double.compare(that.aprc, aprc) == 0
                && installmentType.equals(that.installmentType) && withdrawalDate.equals(that.withdrawalDate)
                && calculationDate.equals(that.calculationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                capital,
                installmentType,
                installmentAmount,
                interestRate,
                withdrawalDate,
                commissionRate,
                age,
                insurance,
                capitalInstallmentSum,
                loanPaidOutAmount,
                commissionAmount,
                insuranceTotalAmount,
                loanTotalCost,
                aprc,
                calculationDate
        );
    }
}
