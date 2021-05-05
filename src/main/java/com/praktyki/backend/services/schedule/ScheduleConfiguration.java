package com.praktyki.backend.services.schedule;

import java.time.LocalDate;
import java.util.Objects;

public class ScheduleConfiguration {

    private double mCapital;
    private InstallmentType mInstallmentType;
    private int mInstallmentAmount;
    private int mInterestRate;
    private LocalDate mWithdrawalDate;

    public double getCapital() {
        return mCapital;
    }

    public InstallmentType getInstallmentType() {
        return mInstallmentType;
    }

    public int getInstallmentAmount() {
        return mInstallmentAmount;
    }

    public int getInterestRate() {
        return mInterestRate;
    }

    public LocalDate getWithdrawalDate() {
        return mWithdrawalDate;
    }

    public ScheduleConfiguration(double capital, InstallmentType installmentType, int installmentAmount,
                                 int interestRate, LocalDate withdrawalDate) {
        mCapital = capital;
        mInstallmentType = installmentType;
        mInstallmentAmount = installmentAmount;
        mInterestRate = interestRate;
        mWithdrawalDate = withdrawalDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleConfiguration that = (ScheduleConfiguration) o;
        return Double.compare(that.getCapital(), getCapital()) == 0
                && getInstallmentAmount() == that.getInstallmentAmount()
                && getInterestRate() == that.getInterestRate()
                && getInstallmentType() == that.getInstallmentType()
                && getWithdrawalDate().equals(that.getWithdrawalDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getCapital(),
                getInstallmentType(),
                getInstallmentAmount(),
                getInterestRate(),
                getWithdrawalDate()
        );
    }
}
