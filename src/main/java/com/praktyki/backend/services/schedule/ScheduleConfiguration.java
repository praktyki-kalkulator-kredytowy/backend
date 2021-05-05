package com.praktyki.backend.services.schedule;

import java.time.LocalDate;
import java.util.Objects;

public class ScheduleConfiguration {

    private double mCapital;
    private InstallmentType mInstallmentType;
    private int mInstallmentAmount;
    private int mInterestRate;
    private LocalDate mWithdrawalDate;

    public static Builder builder(){
        return new Builder();
    }

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

    public ScheduleConfiguration() {}

    public static class Builder {

        private ScheduleConfiguration mScheduleConfiguration = new ScheduleConfiguration();

        public Builder setCapital(double capital) {
            mScheduleConfiguration.mCapital = capital;
            return this;
        }

        public Builder setInstallmentType(InstallmentType installmentType) {
            mScheduleConfiguration.mInstallmentType = installmentType;
            return this;
        }

        public Builder setInstallmentAmount(int installmentAmount) {
            mScheduleConfiguration.mInstallmentAmount = installmentAmount;
            return this;
        }

        public Builder setInterestRate(int interestRate) {
            mScheduleConfiguration.mInterestRate = interestRate;
            return this;
        }

        public Builder setWithdrawalDate(LocalDate withdrawalDate) {
            mScheduleConfiguration.mWithdrawalDate = withdrawalDate;
            return this;
        }

        public ScheduleConfiguration build() {
            return mScheduleConfiguration;
        }

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
