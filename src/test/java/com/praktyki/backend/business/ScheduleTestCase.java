package com.praktyki.backend.business;

import com.praktyki.backend.business.entities.InstallmentType;
import com.praktyki.backend.business.value.Installment;
import com.praktyki.backend.business.value.InsurancePremium;
import com.praktyki.backend.business.value.ScheduleConfiguration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ScheduleTestCase {

    private TestScheduleConfiguration mTestScheduleConfiguration;
    private List<Installment> mInstallmentList;
    private List<InsurancePremium> mInsurancePremiumList;
    private BigDecimal mSumUpCapitalInstallment;
    private BigDecimal mLoanPaidOutAmount;
    private BigDecimal mCommissionAmount;
    private BigDecimal mInsuranceTotalAmount;
    private BigDecimal mLoanTotalCost;
    private BigDecimal mAPRC;

    public TestScheduleConfiguration getTestScheduleConfiguration() {
        return mTestScheduleConfiguration;
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

    public ScheduleTestCase() {}


    public static class TestScheduleConfiguration {

        private BigDecimal mCapital;
        private InstallmentType mInstallmentType;
        private int mInstallmentAmount;
        private double mInterestRate;
        private LocalDate mWithdrawalDate;
        private double mCommissionRate;
        private boolean mInsurance;
        private int mAge;
        private int mMonthFrame;

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

        public double getAge() {
            return mAge;
        }

        public boolean isInsurance() {
            return mInsurance;
        }

        public int getMonthFrame() {
            return mMonthFrame;
        }

        public TestScheduleConfiguration() {}

        public ScheduleConfiguration toScheduleConfiguration() {
            return new ScheduleConfiguration(
                    mCapital,
                    mInstallmentType,
                    mInstallmentAmount,
                    mInterestRate,
                    mWithdrawalDate,
                    mCommissionRate,
                    mInsurance,
                    mAge
            );
        }
    }
}
