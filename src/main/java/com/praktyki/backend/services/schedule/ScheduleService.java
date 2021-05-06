package com.praktyki.backend.services.schedule;

import com.praktyki.backend.MathUtils;
import com.praktyki.backend.services.schedule.dates.DateSchedule;
import com.praktyki.backend.services.schedule.dates.DateScheduleCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    @Autowired
    private DateScheduleCalculator mDateScheduleCalculator;

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

        long timeDifference = ChronoUnit.DAYS.between(
                installmentList.get(installmentList.size()-1).getInstallmentDate(),
                dateSchedule.getDateFor(scheduleConfiguration.getInstallmentAmount())
                );

        BigDecimal interestInstallment = BigDecimal.valueOf(scheduleConfiguration.getInterestRate())
                .multiply(installmentList.get(installmentList.size()-1).getRemainingDebt(), MathUtils.CONTEXT)
                .multiply(BigDecimal.valueOf(timeDifference), MathUtils.CONTEXT)
                .divide(
                        BigDecimal.valueOf(
                                dateSchedule.getDateFor(scheduleConfiguration.getInstallmentAmount()).lengthOfYear()),
                        MathUtils.CONTEXT
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

}
