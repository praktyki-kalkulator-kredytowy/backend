package com.praktyki.backend.services.schedule;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
                    previousInstallment.getRemainingDebt().subtract(previousInstallment.getCapitalInstallment())
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

            BigDecimal capitalInstallment = totalInstallment.subtract(interestInstallment);

            BigDecimal remainingDebt = previousInstallment.getRemainingDebt().subtract(capitalInstallment);

            return new Installment(
                    previousInstallment.getIndex() + 1,
                    installmentDate,
                    capitalInstallment,
                    interestInstallment,
                    remainingDebt
            );
        }



    };

    private static BigDecimal calculateTotalConstantInstallment(ScheduleConfiguration c) {
        return (c.getCapital().multiply(BigDecimal.valueOf(c.getInterestRate())))
                .divide(BigDecimal
                                .valueOf(c.getInstallmentAmount()).multiply(
                                        BigDecimal.ONE.subtract(
                                                BigDecimal.valueOf(c.getInstallmentAmount())
                                                        .divide(BigDecimal
                                                                        .valueOf(c.getInstallmentAmount())
                                                                        .add(BigDecimal.valueOf(c.getInterestRate())),
                                                                RoundingMode.HALF_UP
                                                        ))
                        ),
                        RoundingMode.HALF_UP
                );
    }

    private static BigDecimal calculateInterestInstallment(ScheduleConfiguration conf, Installment prev, LocalDate date) {
        long timeDifference = ChronoUnit.DAYS.between(prev.getInstallmentDate(), date);

        return prev.getRemainingDebt()
                .multiply(BigDecimal.valueOf(conf.getInterestRate()))
                .multiply(BigDecimal.valueOf(timeDifference / (double) date.lengthOfYear()));
    }

    private static Installment createDefaultPrevious(ScheduleConfiguration conf) {
        return new Installment(0,
                conf.getWithdrawalDate(),
                conf.getCapital().divide(BigDecimal.valueOf(conf.getInstallmentAmount()), RoundingMode.HALF_UP),
                BigDecimal.ZERO,
                conf.getCapital());
    }

}
