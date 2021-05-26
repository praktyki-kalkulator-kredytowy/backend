package com.praktyki.backend.web.models;

import java.util.List;
import java.util.Objects;

public class ScheduleModel {
    public ScheduleConfigurationModel scheduleConfiguration;
    public List<PaymentModel> payments;
    public Double capitalInstallmentSum;
    public Double loanPaidOutAmount;
    public Double commissionAmount;
    public Double insuranceTotalAmount;
    public Double loanTotalCost;
    public double aprc;

    public ScheduleModel() {
    }

    public ScheduleModel(
            ScheduleConfigurationModel scheduleConfiguration,
            List<PaymentModel> payments,
            Double capitalInstallmentSum,
            Double loanPaidOutAmount,
            Double commissionAmount,
            Double insuranceTotalAmount,
            Double loanTotalCost,
            double aprc
    ) {
        this.scheduleConfiguration = scheduleConfiguration;
        this.payments = payments;
        this.capitalInstallmentSum = capitalInstallmentSum;
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
}
