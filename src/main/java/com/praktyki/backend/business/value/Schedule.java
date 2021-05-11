package com.praktyki.backend.business.value;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class Schedule {

    private ScheduleConfiguration scheduleConfiguration;
    private List<Installment> installmentList;
    private List<InsurancePremium> insurancePremiumList;
    private BigDecimal loanPaidOutAmount;
    private BigDecimal commissionAmount;
    private BigDecimal insuranceTotalAmount;
    private BigDecimal loanTotalCost;
    
    public static Builder builder(){ return new Builder(); }

    public ScheduleConfiguration getScheduleConfiguration() {
        return scheduleConfiguration;
    }

    public List<Installment> getInstallmentList() {
        return installmentList;
    }

    public List<InsurancePremium> getInsurancePremiumList() {
        return insurancePremiumList;
    }

    public BigDecimal getLoanPaidOutAmount() {
        return loanPaidOutAmount;
    }

    public BigDecimal getCommissionAmount() {
        return commissionAmount;
    }

    public BigDecimal getInsuranceTotalAmount() {
        return insuranceTotalAmount;
    }

    public BigDecimal getLoanTotalCost() {
        return loanTotalCost;
    }

    public Schedule(
            ScheduleConfiguration scheduleConfiguration, List<Installment> installmentList,
            List<InsurancePremium> insurancePremiumList, BigDecimal loanPaidOutAmount, BigDecimal commissionAmount,
            BigDecimal insuranceTotalAmount, BigDecimal loanTotalCost
    ) {
        this.scheduleConfiguration = scheduleConfiguration;
        this.installmentList = installmentList;
        this.insurancePremiumList = insurancePremiumList;
        this.loanPaidOutAmount = loanPaidOutAmount;
        this.commissionAmount = commissionAmount;
        this.insuranceTotalAmount = insuranceTotalAmount;
        this.loanTotalCost = loanTotalCost;
    }
    
    private Schedule(){};

    public static class Builder{
        
        private Schedule mSchedule = new Schedule();

        public Builder setScheduleConfiguration(ScheduleConfiguration scheduleConfiguration) {
            mSchedule.scheduleConfiguration = scheduleConfiguration;
            return this;
        }

        public Builder setInstallmentList(List<Installment> installmentList) {
            mSchedule.installmentList = installmentList;
            return this;
        }

        public Builder setInsurancePremiumList(List<InsurancePremium> insurancePremiumList) {
            mSchedule.insurancePremiumList = insurancePremiumList;
            return this;
        }

        public Builder setLoanPaidOutAmount(BigDecimal loanPaidOutAmount) {
            mSchedule.loanPaidOutAmount = loanPaidOutAmount;
            return this;
        }

        public Builder setCommissionAmount(BigDecimal commissionAmount) {
            mSchedule.commissionAmount = commissionAmount;
            return this;
        }

        public Builder setInsuranceTotalAmount(BigDecimal insuranceTotalAmount) {
            mSchedule.insuranceTotalAmount = insuranceTotalAmount;
            return this;
        }

        public Builder setLoanTotalCost(BigDecimal loanTotalCost) {
            mSchedule.loanTotalCost = loanTotalCost;
            return this;
        }

        public void validate() throws IllegalStateException {

            if(mSchedule.scheduleConfiguration == null
                    || mSchedule.installmentList == null
                    || mSchedule.loanPaidOutAmount == null
                    || mSchedule.commissionAmount == null
                    || mSchedule.insuranceTotalAmount == null
                    || mSchedule.loanTotalCost == null)
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
                && getLoanPaidOutAmount().equals(that.getLoanPaidOutAmount())
                && getCommissionAmount().equals(that.getCommissionAmount())
                && getInsuranceTotalAmount().equals(that.getInsuranceTotalAmount())
                && getLoanTotalCost().equals(that.getLoanTotalCost());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getScheduleConfiguration(),
                getInstallmentList(),
                getLoanPaidOutAmount(),
                getCommissionAmount(),
                getInsuranceTotalAmount(),
                getLoanTotalCost()
        );
    }

    @Override
    public String toString() {
        return "ScheduleInstallmentResult{ " +
                "scheduleConfiguration = " + scheduleConfiguration +
                ", installmentList = " + installmentList +
                ", loanPaidOutAmount = " + loanPaidOutAmount +
                ", commissionAmount = " + commissionAmount +
                ", insuranceTotalAmount = " + insuranceTotalAmount +
                ", loanTotalCost = " + loanTotalCost +
                " }";
    }
}
