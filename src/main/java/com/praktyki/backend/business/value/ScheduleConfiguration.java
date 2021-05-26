package com.praktyki.backend.business.value;

import com.praktyki.backend.business.entities.InstallmentType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class ScheduleConfiguration {

    private BigDecimal mCapital;
    private InstallmentType mInstallmentType;
    private int mInstallmentAmount;
    private double mInterestRate;
    private LocalDate mWithdrawalDate;
    private double mCommissionRate;
    private boolean mInsurance;
    private int mAge;

    public static Builder builder(){
        return new Builder();
    }

    public BigDecimal getCapital() {
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

    public double getCommissionRate() {
        return mCommissionRate;
    }

    public int getAge() {
        return mAge;
    }

    public boolean isInsurance() {
        return mInsurance;
    }

    public ScheduleConfiguration(
            BigDecimal capital,
            InstallmentType installmentType,
            int installmentAmount,
            double interestRate,
            LocalDate withdrawalDate,
            double commissionRate,
            boolean insurance,
            int age
    ) {
        mCapital = capital;
        mInstallmentType = installmentType;
        mInstallmentAmount = installmentAmount;
        mInterestRate = interestRate;
        mWithdrawalDate = withdrawalDate;
        mCommissionRate = commissionRate;
        mInsurance = insurance;
        mAge = age;
    }

    private ScheduleConfiguration() {}

    public static class Builder {

        private ScheduleConfiguration mScheduleConfiguration = new ScheduleConfiguration();

        public Builder setCapital(BigDecimal capital) {
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

        public Builder setInterestRate(double interestRate) {
            mScheduleConfiguration.mInterestRate = interestRate;
            return this;
        }

        public Builder setWithdrawalDate(LocalDate withdrawalDate) {
            mScheduleConfiguration.mWithdrawalDate = withdrawalDate;
            return this;
        }

        public Builder setCommissionRate(double commissionRate) {
            mScheduleConfiguration.mCommissionRate = commissionRate;
            return this;
        }

        public Builder setInsurance(boolean insurance) {
            mScheduleConfiguration.mInsurance = insurance;
            return this;
        }

        public Builder setAge(int age) {
            mScheduleConfiguration.mAge = age;
            return this;
        }

        public void validate() throws IllegalStateException {

            if(mScheduleConfiguration.mWithdrawalDate == null
            || mScheduleConfiguration.mInterestRate == 0.0
            || mScheduleConfiguration.mInstallmentAmount == 0
            || mScheduleConfiguration.mInstallmentType == null
            || mScheduleConfiguration.mCapital == null
            || (mScheduleConfiguration.mCommissionRate < 0 || mScheduleConfiguration.mCommissionRate > 0.2)
            || mScheduleConfiguration.mAge < 0)
                throw new IllegalStateException("Not all parameters specified");

        }

        public ScheduleConfiguration build() {
            validate();
            return mScheduleConfiguration;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleConfiguration that = (ScheduleConfiguration) o;
        return getInstallmentAmount() == that.getInstallmentAmount()
                && Double.compare(that.getInterestRate(), getInterestRate()) == 0
                && getCapital().equals(that.getCapital()) && getInstallmentType() == that.getInstallmentType()
                && getWithdrawalDate().equals(that.getWithdrawalDate())
                && Double.compare(that.getCommissionRate(), getCommissionRate()) == 0
                && Double.compare(that.getAge(), getAge()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getCapital(),
                getInstallmentType(),
                getInstallmentAmount(),
                getInterestRate(),
                getWithdrawalDate(),
                getCommissionRate(),
                getAge());
    }

    @Override
    public String toString() {
        return "ScheduleConfiguration {" +
                " Capital = " + mCapital +
                ", InstallmentType = " + mInstallmentType +
                ", InstallmentAmount = " + mInstallmentAmount +
                ", InterestRate = " + mInterestRate +
                ", WithdrawalDate = " + mWithdrawalDate +
                ", CommissionRate = " + mCommissionRate +
                ", InsuranceRate = " + mAge +
                " }";
    }
}
