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
    public Integer age;
    public boolean insurance;
    public double capitalInstallmentSum;
    public double loanPaidOutAmount;
    public double commissionAmount;
    public double insuranceTotalAmount;
    public double loanTotalCost;
    public double aprc;
    public LocalDate calculationDate;

    public ScheduleCalculationEventModel() {
    }

    public ScheduleCalculationEventModel(
            int id,
            double capital,
            String installmentType,
            int installmentAmount,
            double interestRate,
            LocalDate withdrawalDate,
            double commissionRate,
            Integer age,
            boolean insurance,
            double capitalInstallmentSum,
            double loanPaidOutAmount,
            double commissionAmount,
            double insuranceTotalAmount,
            double loanTotalCost,
            double aprc,
            LocalDate calculationDate
    ) {
        this.id = id;
        this.capital = capital;
        this.installmentType = installmentType;
        this.installmentAmount = installmentAmount;
        this.interestRate = interestRate;
        this.withdrawalDate = withdrawalDate;
        this.commissionRate = commissionRate;
        this.age = age;
        this.insurance = insurance;
        this.capitalInstallmentSum = capitalInstallmentSum;
        this.loanPaidOutAmount = loanPaidOutAmount;
        this.commissionAmount = commissionAmount;
        this.insuranceTotalAmount = insuranceTotalAmount;
        this.loanTotalCost = loanTotalCost;
        this.aprc = aprc;
        this.calculationDate = calculationDate;
    }

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
                && age.equals(that.age) && insurance == that.insurance
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

    @Override
    public String toString() {
        return "ScheduleCalculationEventModel{" +
                "id = " + id +
                ", capital = " + capital +
                ", installmentType = '" + installmentType + '\'' +
                ", installmentAmount = " + installmentAmount +
                ", interestRate = " + interestRate +
                ", withdrawalDate = " + withdrawalDate +
                ", commissionRate = " + commissionRate +
                ", age = " + age +
                ", insurance = " + insurance +
                ", capitalInstallmentSum = " + capitalInstallmentSum +
                ", loanPaidOutAmount = " + loanPaidOutAmount +
                ", commissionAmount = " + commissionAmount +
                ", insuranceTotalAmount = " + insuranceTotalAmount +
                ", loanTotalCost = " + loanTotalCost +
                ", aprc = " + aprc +
                ", calculationDate = " + calculationDate +
                '}';
    }
}
