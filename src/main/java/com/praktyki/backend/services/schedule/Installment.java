package com.praktyki.backend.services.schedule;

import java.time.LocalDate;

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
}