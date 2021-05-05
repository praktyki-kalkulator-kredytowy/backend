package com.praktyki.backend.web.requestModels;

import com.praktyki.backend.services.schedule.InstallmentType;

import java.time.LocalDate;

public class ScheduleConfigurationModel {

    private double mCapital;
    private InstallmentType mInstallmentType;
    private int mInstallmentAmount;
    private double mInterestRate;
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

    public double getInterestRate() {
        return mInterestRate;
    }

    public LocalDate getWithdrawalDate() {
        return mWithdrawalDate;
    }

    public ScheduleConfigurationModel(double capital, InstallmentType installmentType,
                                      int installmentAmount, double interestRate, LocalDate withdrawalDate) {
        mCapital = capital;
        mInstallmentType = installmentType;
        mInstallmentAmount = installmentAmount;
        mInterestRate = interestRate;
        mWithdrawalDate = withdrawalDate;
    }
}
