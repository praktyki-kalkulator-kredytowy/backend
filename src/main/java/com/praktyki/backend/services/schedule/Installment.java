package com.praktyki.backend.services.schedule;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

public class Installment {

    private int mIndex;
    private LocalDate mInstallmentDate;
    private BigDecimal mCapitalInstallment;
    private BigDecimal mInterestInstallment;
    private BigDecimal mRemainingDebt;

    public int getIndex() {
        return mIndex;
    }

    public LocalDate getInstallmentDate() {
        return mInstallmentDate;
    }

    public BigDecimal getCapitalInstallment() {
        return mCapitalInstallment;
    }

    public BigDecimal getInterestInstallment() {
        return mInterestInstallment;
    }

    public BigDecimal getRemainingDebt() {
        return mRemainingDebt;
    }

    public Installment(int index, LocalDate installmentDate, BigDecimal capitalInstallment,
                       BigDecimal interestInstallment, BigDecimal remainingDebt) {
        mIndex = index;
        mInstallmentDate = installmentDate;
        mCapitalInstallment = capitalInstallment.setScale(2, RoundingMode.HALF_UP);
        mInterestInstallment = interestInstallment.setScale(2, RoundingMode.HALF_UP);
        mRemainingDebt = remainingDebt.setScale(2, RoundingMode.HALF_UP);
    }

    private Installment() {}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Installment that = (Installment) o;
        return getIndex() == that.getIndex()
                && getInstallmentDate().equals(that.getInstallmentDate())
                && getCapitalInstallment().equals(that.getCapitalInstallment())
                && getInterestInstallment().equals(that.getInterestInstallment())
                && getRemainingDebt().equals(that.getRemainingDebt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIndex(), getInstallmentDate(), getCapitalInstallment(), getInterestInstallment(), getRemainingDebt());
    }

    @Override
    public String toString() {
        return "Installment { " +
                " Index = " + mIndex +
                ", InstallmentDate = " + mInstallmentDate +
                ", CapitalInstallment = " + mCapitalInstallment +
                ", InterestInstallment = " + mInterestInstallment +
                ", RemainingDebt = " + mRemainingDebt +
                " }";
    }
}
