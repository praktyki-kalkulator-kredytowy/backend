package com.praktyki.backend.web.models;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

public class ScheduleModel {

    @Valid
    @NotNull
    public ScheduleConfigurationModel scheduleConfiguration;

    @Valid
    @NotNull
    @Size(min = 1)
    public List<PaymentModel> payments;

    @Positive
    public Double capitalInstallmentSum;

    @Positive
    public Double interestInstallmentSum;

    @Positive
    public Double loanPaidOutAmount;

    @Positive
    public Double commissionAmount;

    @Positive
    public Double insuranceTotalAmount;

    @Positive
    public Double loanTotalCost;

    @Positive
    public Double aprc;

    public ScheduleModel() {
    }

    public ScheduleModel(
            ScheduleConfigurationModel scheduleConfiguration,
            List<PaymentModel> payments,
            Double capitalInstallmentSum,
            Double interestInstallmentSum,
            Double loanPaidOutAmount,
            Double commissionAmount,
            Double insuranceTotalAmount,
            Double loanTotalCost,
            Double aprc
    ) {
        this.scheduleConfiguration = scheduleConfiguration;
        this.payments = payments;
        this.capitalInstallmentSum = capitalInstallmentSum;
        this.interestInstallmentSum = interestInstallmentSum;
        this.loanPaidOutAmount = loanPaidOutAmount;
        this.commissionAmount = commissionAmount;
        this.insuranceTotalAmount = insuranceTotalAmount;
        this.loanTotalCost = loanTotalCost;
        this.aprc = aprc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleModel that = (ScheduleModel) o;
        return Double.compare(that.aprc, aprc) == 0
                && scheduleConfiguration.equals(that.scheduleConfiguration)
                && payments.equals(that.payments)
                && capitalInstallmentSum.equals(that.capitalInstallmentSum)
                && loanPaidOutAmount.equals(that.loanPaidOutAmount)
                && commissionAmount.equals(that.commissionAmount)
                && insuranceTotalAmount.equals(that.insuranceTotalAmount)
                && loanTotalCost.equals(that.loanTotalCost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                scheduleConfiguration,
                payments,
                capitalInstallmentSum,
                loanPaidOutAmount,
                commissionAmount,
                insuranceTotalAmount,
                loanTotalCost,
                aprc
        );
    }

    @Override
    public String toString() {
        return "ScheduleModel{" +
                "scheduleConfiguration = " + scheduleConfiguration +
                ", payments = " + payments +
                ", capitalInstallmentSum = " + capitalInstallmentSum +
                ", interestInstallmentSum = " + interestInstallmentSum +
                ", loanPaidOutAmount = " + loanPaidOutAmount +
                ", commissionAmount = " + commissionAmount +
                ", insuranceTotalAmount = " + insuranceTotalAmount +
                ", loanTotalCost = " + loanTotalCost +
                ", aprc = " + aprc +
                '}';
    }
}
