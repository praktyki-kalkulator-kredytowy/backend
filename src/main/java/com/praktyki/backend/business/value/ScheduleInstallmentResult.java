package com.praktyki.backend.business.value;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class ScheduleInstallmentResult {

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

    public ScheduleInstallmentResult(
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
    
    private ScheduleInstallmentResult(){};

    public static class Builder{
        
        private ScheduleInstallmentResult mScheduleInstallmentResult = new ScheduleInstallmentResult();

        public Builder setScheduleConfiguration(ScheduleConfiguration scheduleConfiguration) {
            mScheduleInstallmentResult.scheduleConfiguration = scheduleConfiguration;
            return this;
        }

        public Builder setInstallmentList(List<Installment> installmentList) {
            mScheduleInstallmentResult.installmentList = installmentList;
            return this;
        }

        public Builder setInsurancePremiumList(List<InsurancePremium> insurancePremiumList) {
            mScheduleInstallmentResult.insurancePremiumList = insurancePremiumList;
            return this;
        }

        public Builder setLoanPaidOutAmount(BigDecimal loanPaidOutAmount) {
            mScheduleInstallmentResult.loanPaidOutAmount = loanPaidOutAmount;
            return this;
        }

        public Builder setCommissionAmount(BigDecimal commissionAmount) {
            mScheduleInstallmentResult.commissionAmount = commissionAmount;
            return this;
        }

        public Builder setInsuranceTotalAmount(BigDecimal insuranceTotalAmount) {
            mScheduleInstallmentResult.insuranceTotalAmount = insuranceTotalAmount;
            return this;
        }

        public Builder setLoanTotalCost(BigDecimal loanTotalCost) {
            mScheduleInstallmentResult.loanTotalCost = loanTotalCost;
            return this;
        }

        public void validate() throws IllegalStateException {

            if(mScheduleInstallmentResult.scheduleConfiguration == null
                    || mScheduleInstallmentResult.installmentList == null
                    || mScheduleInstallmentResult.loanPaidOutAmount == null
                    || mScheduleInstallmentResult.commissionAmount == null
                    || mScheduleInstallmentResult.insuranceTotalAmount == null
                    || mScheduleInstallmentResult.loanTotalCost == null)
                throw new IllegalStateException("Not all parameters specified");

        }

        public ScheduleInstallmentResult build(){
            return mScheduleInstallmentResult;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleInstallmentResult that = (ScheduleInstallmentResult) o;
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
