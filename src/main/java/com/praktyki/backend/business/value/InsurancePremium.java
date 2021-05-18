package com.praktyki.backend.business.value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class InsurancePremium implements Payment {

    private int mIndex;
    private LocalDate mInsurancePremiumDate;
    private BigDecimal mInsurancePremiumValue;

    public int getIndex() {
        return mIndex;
    }

    public LocalDate getInsurancePremiumDate() {
        return mInsurancePremiumDate;
    }

    public BigDecimal getInsurancePremiumValue() {
        return mInsurancePremiumValue;
    }

    public InsurancePremium(int index, LocalDate insurancePremiumDate, BigDecimal insurancePremiumValue) {
        mIndex = index;
        mInsurancePremiumDate = insurancePremiumDate;
        mInsurancePremiumValue = insurancePremiumValue;
    }

    private InsurancePremium() {}

    @Override
    public LocalDate getDate() {
        return getInsurancePremiumDate();
    }

    @Override
    public BigDecimal getAmount() {
        return getInsurancePremiumValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InsurancePremium that = (InsurancePremium) o;
        return getIndex() == that.getIndex()
                && getInsurancePremiumDate().equals(that.getInsurancePremiumDate())
                && getInsurancePremiumValue().equals(that.getInsurancePremiumValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIndex(), getInsurancePremiumDate(), getInsurancePremiumValue());
    }

    @Override
    public String toString() {
        return "InsurancePremium{ " +
                "mIndex = " + mIndex +
                ", mInsurancePremiumDate = " + mInsurancePremiumDate +
                ", mInsurancePremiumValue = " + mInsurancePremiumValue +
                " }";
    }
}
