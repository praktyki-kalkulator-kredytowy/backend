package com.praktyki.backend.business.services.schedule;

import com.praktyki.backend.business.value.Installment;
import com.praktyki.backend.business.value.ScheduleConfiguration;
import com.praktyki.backend.business.services.schedule.dates.DateSchedule;
import com.praktyki.backend.business.services.schedule.dates.DateScheduleCalculator;
import com.praktyki.backend.business.utils.InstallmentUtils;
import com.praktyki.backend.business.utils.MathUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ScheduleService {

    private final DateScheduleCalculator mDateScheduleCalculator;

    public ScheduleService(DateScheduleCalculator dateScheduleCalculator) {
        mDateScheduleCalculator = dateScheduleCalculator;
    }

    public List<Installment> createSchedule(ScheduleConfiguration scheduleConfiguration) {

        DateSchedule dateSchedule = mDateScheduleCalculator.calculate(scheduleConfiguration.getWithdrawalDate());

        List<Installment> installmentList = new LinkedList<>();

        BigDecimal capitalInstallmentSum = BigDecimal.ZERO;

        for(int i = 0; i < scheduleConfiguration.getInstallmentAmount() - 1; i++ ) {

            installmentList.add(scheduleConfiguration.getInstallmentType().calculate(
                    installmentList.isEmpty() ? null : installmentList.get(i - 1),
                    scheduleConfiguration,
                    dateSchedule.getDateFor(i + 1)
            ));

            capitalInstallmentSum = capitalInstallmentSum.add(
                    installmentList.get(i).getCapitalInstallment(), MathUtils.CONTEXT);
        }

        BigDecimal interestInstallment = InstallmentUtils.calculateInterestInstallment(
                scheduleConfiguration,
                installmentList.get(installmentList.size()-1),
                dateSchedule.getDateFor(scheduleConfiguration.getInstallmentAmount())
        );


        installmentList.add(new Installment(
                        scheduleConfiguration.getInstallmentAmount(),
                        dateSchedule.getDateFor(scheduleConfiguration.getInstallmentAmount()),
                        scheduleConfiguration.getCapital().subtract(capitalInstallmentSum, MathUtils.CONTEXT),
                        interestInstallment,
                        BigDecimal.ZERO
                        ));

        return installmentList.stream().peek(Installment::round).collect(Collectors.toList());

    }

    public BigDecimal calculateCommission(ScheduleConfiguration scheduleConfiguration) {

        BigDecimal commission = scheduleConfiguration.getCapital().multiply(
                BigDecimal.valueOf(scheduleConfiguration.getCommissionRate()), MathUtils.CONTEXT);

        return commission.compareTo(new BigDecimal("50")) < 0
                ? new BigDecimal("50").setScale(2, RoundingMode.HALF_UP)
                : commission.setScale(2, RoundingMode.HALF_UP);

    }

}
