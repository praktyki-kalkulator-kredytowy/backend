package com.praktyki.backend.services.schedule;

import java.time.LocalDate;
import java.util.Objects;

public class Installment {

    private int mIndex;
    private LocalDate mInstallmentDate;
    private double mCapitalInstallment;
    private double mInterestInstallment;
    private double mRemainingDebt;

    public int getIndex() {
        return mIndex;
    }

    public LocalDate getInstallmentDate() {
        return mInstallmentDate;
    }

    public double getCapitalInstallment() {
        return mCapitalInstallment;
    }

    public double getInterestInstallment() {
        return mInterestInstallment;
    }

    public double getRemainingDebt() {
        return mRemainingDebt;
    }

    public Installment(int index, LocalDate InstallmentDate, double capitalInstallment,
                       double interestInstallment, double remainingDebt) {
        mIndex = index;
        mInstallmentDate = InstallmentDate;
        mCapitalInstallment = capitalInstallment;
        mInterestInstallment = interestInstallment;
        mRemainingDebt = remainingDebt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Installment that = (Installment) o;
        return getIndex() == that.getIndex()
                && Double.compare(that.getCapitalInstallment(), getCapitalInstallment()) == 0
                && Double.compare(that.getInterestInstallment(), getInterestInstallment()) == 0
                && Double.compare(that.getRemainingDebt(), getRemainingDebt()) == 0
                && getInstallmentDate().equals(that.getInstallmentDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getIndex(),
                getInstallmentDate(),
                getCapitalInstallment(),
                getInterestInstallment(),
                getRemainingDebt()
        );
    }
}
