package com.praktyki.backend.business.value;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentImpl implements Payment {

    private LocalDate mDate;

    private BigDecimal mAmount;

    @Override
    public LocalDate getDate() {
        return mDate;
    }

    @Override
    public BigDecimal getAmount() {
        return mAmount;
    }

    public PaymentImpl(LocalDate date, BigDecimal amount) {
        mDate = date;
        mAmount = amount;
    }

}
