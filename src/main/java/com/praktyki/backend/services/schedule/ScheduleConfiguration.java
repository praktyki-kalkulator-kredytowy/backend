package com.praktyki.backend.services.schedule;

import java.time.LocalDate;

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
}
