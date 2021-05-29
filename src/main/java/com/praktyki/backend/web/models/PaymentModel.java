package com.praktyki.backend.web.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.Objects;

public class PaymentModel {

    @Positive
    public int index;

    @NotNull(message = "Please specify a date")
    public LocalDate date;

    @Min(value = 0, message = "Capital must be 0 or higher")
    public Double capitalInstallment;

    @Min(value = 0, message = "InterestInstallment must be 0 or higher")
    public Double interestInstallment;

    @Min(value = 0, message = "RemainingDebt must be 0 or higher")
    public Double remainingDebt;

    @Min(value = 0, message = "InsurancePremium must be 0 or higher")
    public Double insurancePremium;

    public PaymentModel() {
    }

    public PaymentModel(
            int index,
            LocalDate date,
            Double capitalInstallment,
            Double interestInstallment,
            Double remainingDebt,
            Double insurancePremium
    ) {
        this.index = index;
        this.date = date;
        this.capitalInstallment = capitalInstallment;
        this.interestInstallment = interestInstallment;
        this.remainingDebt = remainingDebt;
        this.insurancePremium = insurancePremium;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentModel that = (PaymentModel) o;
        return index == that.index
                && date.equals(that.date)
                && capitalInstallment.equals(that.capitalInstallment)
                && interestInstallment.equals(that.interestInstallment)
                && remainingDebt.equals(that.remainingDebt)
                && Objects.equals(insurancePremium, that.insurancePremium);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, date, capitalInstallment, interestInstallment, remainingDebt, insurancePremium);
    }

    @Override
    public String toString() {
        return "PaymentModel{" +
                "index = " + index +
                ", date = " + date +
                ", capitalInstallment = " + capitalInstallment +
                ", interestInstallment = " + interestInstallment +
                ", remainingDebt = " + remainingDebt +
                ", insurancePremium = " + insurancePremium +
                '}';
    }
}
