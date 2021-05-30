package com.praktyki.backend.business.utils;

import com.praktyki.backend.business.value.Installment;
import com.praktyki.backend.business.value.ScheduleConfiguration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class InstallmentUtils {

    public static BigDecimal calculateTotalConstantInstallment(ScheduleConfiguration conf) {

        if(conf.getInterestRate() == 0)
            return conf.getCapital().divide(
                    BigDecimal.valueOf(conf.getInstallmentAmount()),2, RoundingMode.HALF_UP
            );

        BigDecimal c = conf.getCapital();
        BigDecimal r = BigDecimal.valueOf(conf.getInterestRate());
        //TODO: k - how much rates in year
        BigDecimal k = BigDecimal.valueOf(12);
        BigDecimal n = BigDecimal.valueOf(conf.getInstallmentAmount());

        BigDecimal nominator = c.multiply(r, MathUtils.CONTEXT);

        BigDecimal smallFraction = k.divide(k.add(r), MathUtils.CONTEXT);

        BigDecimal smallFractionToPower = smallFraction.pow(conf.getInstallmentAmount(), MathUtils.CONTEXT);

        BigDecimal denominator = k.multiply(
                BigDecimal.ONE.subtract(smallFractionToPower, MathUtils.CONTEXT),
                MathUtils.CONTEXT
        );

        return nominator.divide(denominator, MathUtils.CONTEXT).setScale(2, RoundingMode.HALF_UP);

    }

    public static BigDecimal calculateInterestInstallment(ScheduleConfiguration conf, Installment prev, LocalDate date) {
        long timeDifference = ChronoUnit.DAYS.between(prev.getInstallmentDate(), date);


        return prev.getRemainingDebt()
                .multiply(BigDecimal.valueOf(conf.getInterestRate()), MathUtils.CONTEXT)
                .multiply(BigDecimal.valueOf(timeDifference).divide(BigDecimal.valueOf(360), MathUtils.CONTEXT), MathUtils.CONTEXT)
                .setScale(2,RoundingMode.HALF_UP);
    }
}
