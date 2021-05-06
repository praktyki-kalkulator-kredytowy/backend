package com.praktyki.backend.services.schedule;

import com.praktyki.backend.utils.InstallmentUtils;
import com.praktyki.backend.utils.MathUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public enum InstallmentType implements InstallmentCalculator {

    DECREASING {
        @Override
        public Installment calculate(Installment previousInstallment, ScheduleConfiguration scheduleConfiguration, LocalDate installmentDate) {
            if(previousInstallment == null)
                previousInstallment = createDefaultPreviousForDecreasing(scheduleConfiguration);

            return new Installment(
                    previousInstallment.getIndex() + 1,
                    installmentDate,
                    previousInstallment.getCapitalInstallment(),
                    InstallmentUtils.calculateInterestInstallment(scheduleConfiguration, previousInstallment, installmentDate),
                    previousInstallment.getRemainingDebt().subtract(previousInstallment.getCapitalInstallment(), MathUtils.CONTEXT)
            );
        }
    },
    CONSTANT {
        @Override
        public Installment calculate(Installment previousInstallment, ScheduleConfiguration scheduleConfiguration, LocalDate installmentDate) {
            if(previousInstallment == null)
                previousInstallment = createDefaultPreviousForConstant(scheduleConfiguration);

            BigDecimal totalInstallment = InstallmentUtils.calculateTotalConstantInstallment(scheduleConfiguration);

            BigDecimal interestInstallment = InstallmentUtils.calculateInterestInstallment(scheduleConfiguration, previousInstallment, installmentDate);

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

    private static Installment createDefaultPreviousForDecreasing(ScheduleConfiguration conf) {
        return new Installment(0,
                conf.getWithdrawalDate(),
                conf.getCapital().divide(BigDecimal.valueOf(conf.getInstallmentAmount()), MathUtils.CONTEXT).setScale(2, RoundingMode.HALF_UP),
                BigDecimal.ZERO,
                conf.getCapital());
    }

    private static Installment createDefaultPreviousForConstant(ScheduleConfiguration scheduleConfiguration) {
        return new Installment(
              0,
              scheduleConfiguration.getWithdrawalDate(),
                InstallmentUtils.calculateTotalConstantInstallment(scheduleConfiguration),
                BigDecimal.ZERO,
                scheduleConfiguration.getCapital()
        );
    }

}
