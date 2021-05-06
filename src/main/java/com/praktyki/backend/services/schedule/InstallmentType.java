package com.praktyki.backend.services.schedule;

import com.praktyki.backend.MathUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public enum InstallmentType implements InstallmentCalculator {

    DECREASING {
        @Override
        public Installment calculate(Installment previousInstallment, ScheduleConfiguration scheduleConfiguration, LocalDate installmentDate) {
            if(previousInstallment == null)
                previousInstallment = createDefaultPrevious(scheduleConfiguration);

            return new Installment(
                    previousInstallment.getIndex() + 1,
                    installmentDate,
                    previousInstallment.getCapitalInstallment(),
                    calculateInterestInstallment(scheduleConfiguration, previousInstallment, installmentDate),
                    previousInstallment.getRemainingDebt().subtract(previousInstallment.getCapitalInstallment(), MathUtils.CONTEXT)
            );
        }
    },
    CONSTANT {
        @Override
        public Installment calculate(Installment previousInstallment, ScheduleConfiguration scheduleConfiguration, LocalDate installmentDate) {
            if(previousInstallment == null)
                previousInstallment = createDefaultPrevious(scheduleConfiguration);

            BigDecimal totalInstallment = calculateTotalConstantInstallment(scheduleConfiguration);

            BigDecimal interestInstallment = calculateInterestInstallment(scheduleConfiguration, previousInstallment, installmentDate);

            BigDecimal capitalInstallment = totalInstallment.subtract(interestInstallment, MathUtils.CONTEXT);

            BigDecimal remainingDebt = previousInstallment.getRemainingDebt().subtract(capitalInstallment, MathUtils.CONTEXT);

            return new Installment(
                    previousInstallment.getIndex() + 1,
                    installmentDate,
                    capitalInstallment,
                    interestInstallment,
                    remainingDebt
            );
        }



    };

    private static BigDecimal calculateTotalConstantInstallment(ScheduleConfiguration conf) {
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

        return nominator.divide(denominator, MathUtils.CONTEXT);

    }

    private static BigDecimal calculateInterestInstallment(ScheduleConfiguration conf, Installment prev, LocalDate date) {
        long timeDifference = ChronoUnit.DAYS.between(prev.getInstallmentDate(), date);

        return prev.getRemainingDebt()
                .multiply(BigDecimal.valueOf(conf.getInterestRate()), MathUtils.CONTEXT)
                .multiply(BigDecimal.valueOf(timeDifference).divide(BigDecimal.valueOf(date.lengthOfYear()), MathUtils.CONTEXT), MathUtils.CONTEXT);
    }

    private static Installment createDefaultPrevious(ScheduleConfiguration conf) {
        return new Installment(0,
                conf.getWithdrawalDate(),
                conf.getCapital().divide(BigDecimal.valueOf(conf.getInstallmentAmount()), MathUtils.CONTEXT),
                BigDecimal.ZERO,
                conf.getCapital());
    }

}
