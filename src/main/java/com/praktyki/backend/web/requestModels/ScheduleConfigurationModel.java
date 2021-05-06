package com.praktyki.backend.web.requestModels;

import com.praktyki.backend.services.schedule.InstallmentType;

import java.time.LocalDate;
import java.util.Date;

public class ScheduleConfigurationModel {

    private double capital;
    private InstallmentType installmentType;
    private int installmentAmount;
    private double interestRate;
    private Date withdrawalDate;

    public double getCapital() {
        return capital;
    }

    public InstallmentType getInstallmentType() {
        return installmentType;
    }

    public int getInstallmentAmount() {
        return installmentAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public Date getWithdrawalDate() {
        return withdrawalDate;
    }

    public ScheduleConfigurationModel(double capital, InstallmentType installmentType,
                                      int installmentAmount, double interestRate, Date withdrawalDate) {
        this.capital = capital;
        this.installmentType = installmentType;
        this.installmentAmount = installmentAmount;
        this.interestRate = interestRate;
        this.withdrawalDate = withdrawalDate;
    }
}
