package com.praktyki.backend.web.models;

import javax.validation.Valid;
import javax.validation.constraints.Min;
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

    @Min(value = 0, message = "CapitalInstallmentSum must be 0 or higher")
    public Double capitalInstallmentSum;

    @Min(value = 0, message = "InterestInstallmentSum must be 0 or higher")
    public Double interestInstallmentSum;

    @Min(value = 0, message = "LoanPaidOutAmount must be 0 or higher")
    public Double loanPaidOutAmount;

    @Min(value = 0, message = "CommissionAmount must be 0 or higher")
    public Double commissionAmount;

    @Min(value = 0, message = "InsuranceTotalAmount must be 0 or higher")
    public Double insuranceTotalAmount;

    @Min(value = 0, message = "LoanTotalCost must be 0 or higher")
    public Double loanTotalCost;

    @Min(value = 0, message = "Aprc must be 0 or higher")
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
