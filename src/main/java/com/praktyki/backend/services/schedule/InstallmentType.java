package com.praktyki.backend.services.schedule;

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
                    previousInstallment.getRemainingDebt() - previousInstallment.getCapitalInstallment()
                    );
        }
    },
    CONSTANT {
        @Override
        public Installment calculate(Installment previousInstallment, ScheduleConfiguration scheduleConfiguration, LocalDate installmentDate) {
            if(previousInstallment == null)
                previousInstallment = createDefaultPrevious(scheduleConfiguration);

            double totalInstallment = calculateTotalConstantInstallment(scheduleConfiguration);

            double interestInstallment = calculateInterestInstallment(scheduleConfiguration, previousInstallment, installmentDate);

            double capitalInstallment = totalInstallment - interestInstallment;

            double remainingDebt = previousInstallment.getRemainingDebt() - capitalInstallment;

            return new Installment(
                    previousInstallment.getIndex() + 1,
                    installmentDate,
                    capitalInstallment,
                    interestInstallment,
                    remainingDebt
            );
        }



    };

    private static double calculateTotalConstantInstallment(ScheduleConfiguration c) {
        return (c.getCapital() * c.getInterestRate())
                / (c.getInstallmentAmount()
                * (1 - Math.pow((c.getInstallmentAmount() / (c.getInstallmentAmount() + c.getInterestRate())),
                c.getInstallmentAmount())));
    }

    private static double calculateInterestInstallment(ScheduleConfiguration conf, Installment prev, LocalDate date) {
        long timeDifference = ChronoUnit.DAYS.between(prev.getInstallmentDate(), date);

        return prev.getRemainingDebt()
                        * conf.getInterestRate()
                        *  (timeDifference / (double) date.lengthOfYear());
    }

    private static Installment createDefaultPrevious(ScheduleConfiguration conf) {
        return new Installment(0,
                conf.getWithdrawalDate(),
                conf.getCapital() / conf.getInstallmentAmount(),
                0,
                conf.getCapital());
    }

}
