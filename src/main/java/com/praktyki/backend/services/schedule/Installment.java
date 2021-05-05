package com.praktyki.backend.services.schedule;

import java.time.LocalDate;
import java.util.Objects;

public class Installment {

    private int mId;
    private LocalDate mLocalDate;
    private double mCapitalInstallment;
    private double mInterestInstallment;
    private double mRemainingDebt;

    public int getId() {
        return mId;
    }

    public LocalDate getLocalDate() {
        return mLocalDate;
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

    public Installment(int id, LocalDate localDate, double capitalInstallment,
                       double interestInstallment, double remainingDebt) {
        mId = id;
        mLocalDate = localDate;
        mCapitalInstallment = capitalInstallment;
        mInterestInstallment = interestInstallment;
        mRemainingDebt = remainingDebt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Installment that = (Installment) o;
        return getId() == that.getId()
                && Double.compare(that.getCapitalInstallment(), getCapitalInstallment()) == 0
                && Double.compare(that.getInterestInstallment(), getInterestInstallment()) == 0
                && Double.compare(that.getRemainingDebt(), getRemainingDebt()) == 0
                && getLocalDate().equals(that.getLocalDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getId(),
                getLocalDate(),
                getCapitalInstallment(),
                getInterestInstallment(),
                getRemainingDebt()
        );
    }
}
