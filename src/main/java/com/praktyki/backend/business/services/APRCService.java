package com.praktyki.backend.business.services;

import com.praktyki.backend.business.utils.MathUtils;
import com.praktyki.backend.business.value.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.function.UnaryOperator;

public class APRCService {

    public static final double RANGE_START = 0;
    public static final double RANGE_END = 1000;
    public static final double PRECISION = 0.0000001;

    public BigDecimal calculateAPRC(
            ScheduleConfiguration conf,
            List<Installment> installments,
            List<InsurancePremium> premiums,
            BigDecimal commission
            ) {

        List<Payment> payments = new LinkedList<>();
        payments.addAll(installments);
        payments.addAll(premiums);
        payments.add(new PaymentImpl(
                conf.getWithdrawalDate(),
                commission
        ));


        UnaryOperator<Double> APRCFunction = createAPRCFunction(
                conf.getCapital(),
                payments,
                conf.getWithdrawalDate()
        );

        if(BigDecimal.valueOf(APRCFunction.apply(0D))
                .setScale(4,RoundingMode.HALF_UP)
                .compareTo(BigDecimal.ZERO) == 0)
            return BigDecimal.ZERO.setScale(4, RoundingMode.HALF_UP);

        return BigDecimal.valueOf(MathUtils.solveForZeroWithBisection(
                APRCFunction,
                RANGE_START,
                RANGE_END,
                PRECISION
        )).setScale(4, RoundingMode.HALF_UP);
    }

    private UnaryOperator<Double> createAPRCFunction(
            BigDecimal capital,
            List<Payment> payments,
            LocalDate withdrawalDate) { return x -> {

        double sum = 0;

        for(Payment p : payments) {
            double yearProgress = (double) ChronoUnit.DAYS.between(withdrawalDate, p.getDate())
                    / 360;

             sum +=
                    p.getAmount().doubleValue() / Math.pow(1 + x, yearProgress);
        }

        return capital.doubleValue() - sum;
    };}





}
