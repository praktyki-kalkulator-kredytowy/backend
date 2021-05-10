package com.praktyki.backend.business.utils;

import com.praktyki.backend.business.entities.Installment;
import com.praktyki.backend.business.entities.ScheduleConfiguration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class InstallmentUtils {

    public static BigDecimal calculateTotalConstantInstallment(ScheduleConfiguration conf) {
        BigDecimal c = conf.getCapital();
        BigDecimal r = BigDecimal.valueOf(conf.getInterestRate());
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
                .multiply(BigDecimal.valueOf(timeDifference).divide(BigDecimal.valueOf(date.lengthOfYear()), MathUtils.CONTEXT), MathUtils.CONTEXT)
                .setScale(2,RoundingMode.HALF_UP);
    }
}
