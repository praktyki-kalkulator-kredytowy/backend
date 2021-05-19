package com.praktyki.backend.business.value;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class Schedule {

    private ScheduleConfiguration mScheduleConfiguration;
    private List<Installment> mInstallmentList;
    private List<InsurancePremium> mInsurancePremiumList;
    private BigDecimal mSumUpCapitalInstallment;
    private BigDecimal mLoanPaidOutAmount;
    private BigDecimal mCommissionAmount;
    private BigDecimal mInsuranceTotalAmount;
    private BigDecimal mLoanTotalCost;
    private BigDecimal mAPRC;
    
    public static Builder builder(){ return new Builder(); }

    public ScheduleConfiguration getScheduleConfiguration() {
        return mScheduleConfiguration;
    }

    public List<Installment> getInstallmentList() {
        return mInstallmentList;
    }

    public List<InsurancePremium> getInsurancePremiumList() {
        return mInsurancePremiumList;
    }

    public BigDecimal getSumUpCapitalInstallment() {
        return mSumUpCapitalInstallment;
    }

    public BigDecimal getLoanPaidOutAmount() {
        return mLoanPaidOutAmount;
    }

    public BigDecimal getCommissionAmount() {
        return mCommissionAmount;
    }

    public BigDecimal getInsuranceTotalAmount() {
        return mInsuranceTotalAmount;
    }

    public BigDecimal getLoanTotalCost() {
        return mLoanTotalCost;
    }

    public BigDecimal getAPRC() {
        return mAPRC;
    }

    public Schedule(
            ScheduleConfiguration scheduleConfiguration, List<Installment> installmentList,
            List<InsurancePremium> insurancePremiumList, BigDecimal sumUpCapitalInstallment,
            BigDecimal loanPaidOutAmount, BigDecimal commissionAmount, BigDecimal insuranceTotalAmount,
            BigDecimal loanTotalCost, BigDecimal APRC
    ) {
        this.mScheduleConfiguration = scheduleConfiguration;
        this.mInstallmentList = installmentList;
        this.mInsurancePremiumList = insurancePremiumList;
        this.mSumUpCapitalInstallment = sumUpCapitalInstallment;
        this.mLoanPaidOutAmount = loanPaidOutAmount;
        this.mCommissionAmount = commissionAmount;
        this.mInsuranceTotalAmount = insuranceTotalAmount;
        this.mLoanTotalCost = loanTotalCost;
        this.mAPRC = APRC;
    }
    
    private Schedule(){};

    public static class Builder{
        
        private Schedule mSchedule = new Schedule();

        public Builder setScheduleConfiguration(ScheduleConfiguration scheduleConfiguration) {
            mSchedule.mScheduleConfiguration = scheduleConfiguration;
            return this;
        }

        public Builder setInstallmentList(List<Installment> installmentList) {
            mSchedule.mInstallmentList = installmentList;
            return this;
        }

        public Builder setInsurancePremiumList(List<InsurancePremium> insurancePremiumList) {
            mSchedule.mInsurancePremiumList = insurancePremiumList;
            return this;
        }

        public Builder setSumUpCapitalInstallment(BigDecimal sumUpCapitalInstallment){
            mSchedule.mSumUpCapitalInstallment = sumUpCapitalInstallment;
            return this;
        }

        public Builder setLoanPaidOutAmount(BigDecimal loanPaidOutAmount) {
            mSchedule.mLoanPaidOutAmount = loanPaidOutAmount;
            return this;
        }

        public Builder setCommissionAmount(BigDecimal commissionAmount) {
            mSchedule.mCommissionAmount = commissionAmount;
            return this;
        }

        public Builder setInsuranceTotalAmount(BigDecimal insuranceTotalAmount) {
            mSchedule.mInsuranceTotalAmount = insuranceTotalAmount;
            return this;
        }

        public Builder setLoanTotalCost(BigDecimal loanTotalCost) {
            mSchedule.mLoanTotalCost = loanTotalCost;
            return this;
        }

        public Builder setAPRC(BigDecimal APRC) {
            mSchedule.mAPRC = APRC;
            return this;
        }

        public void validate() throws IllegalStateException {

            if(mSchedule.mScheduleConfiguration == null
                    || mSchedule.mInstallmentList == null
                    || mSchedule.mInsurancePremiumList == null
                    || mSchedule.mSumUpCapitalInstallment == null
                    || mSchedule.mLoanPaidOutAmount == null
                    || mSchedule.mCommissionAmount == null
                    || mSchedule.mInsuranceTotalAmount == null
                    || mSchedule.mLoanTotalCost == null
                    || mSchedule.mAPRC == null)
                throw new IllegalStateException("Not all parameters specified");

        }

        public Schedule build(){
            return mSchedule;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule that = (Schedule) o;
        return getScheduleConfiguration().equals(that.getScheduleConfiguration())
                && getInstallmentList().equals(that.getInstallmentList())
                && getInsurancePremiumList().equals(that.getInsurancePremiumList())
                && getSumUpCapitalInstallment().equals(that.getSumUpCapitalInstallment())
                && getLoanPaidOutAmount().equals(that.getLoanPaidOutAmount())
                && getCommissionAmount().equals(that.getCommissionAmount())
                && getInsuranceTotalAmount().equals(that.getInsuranceTotalAmount())
                && getLoanTotalCost().equals(that.getLoanTotalCost())
                && getAPRC().equals(that.getAPRC());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getScheduleConfiguration(),
                getInstallmentList(),
                getInsurancePremiumList(),
                getSumUpCapitalInstallment(),
                getLoanPaidOutAmount(),
                getCommissionAmount(),
                getInsuranceTotalAmount(),
                getLoanTotalCost(),
                getAPRC()
        );
    }

    @Override
    public String toString() {
        return "ScheduleInstallmentResult{ " +
                "scheduleConfiguration = " + mScheduleConfiguration +
                ", installmentList = " + mInstallmentList +
                ", insuranceList = " + mInsurancePremiumList +
                ", sumUpCapitalInstallment = " + mSumUpCapitalInstallment +
                ", loanPaidOutAmount = " + mLoanPaidOutAmount +
                ", commissionAmount = " + mCommissionAmount +
                ", insuranceTotalAmount = " + mInsuranceTotalAmount +
                ", loanTotalCost = " + mLoanTotalCost +
                ", APRC = " + mAPRC +
                " }";
    }
}
