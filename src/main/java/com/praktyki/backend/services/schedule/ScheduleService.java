package com.praktyki.backend.services.schedule;

import com.praktyki.backend.services.schedule.dates.DateSchedule;
import com.praktyki.backend.services.schedule.dates.DateScheduleCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private DateScheduleCalculator mDateScheduleCalculator;

    public List<Installment> createSchedule(ScheduleConfiguration scheduleConfiguration) {

        DateSchedule dateSchedule = mDateScheduleCalculator.calculate(scheduleConfiguration.getWithdrawalDate());

        List<Installment> installmentList = new LinkedList<>();

        double capitalInstallmentSum = 0;

        for(int i = 0; i < scheduleConfiguration.getInstallmentAmount() - 1; i++ ) {

            installmentList.add(scheduleConfiguration.getInstallmentType().calculate(
                    installmentList.isEmpty() ? null : installmentList.get(i - 1),
                    scheduleConfiguration,
                    dateSchedule.getDateFor(i + 1)
            ));

            capitalInstallmentSum += installmentList.get(i).getCapitalInstallment();
        }

        long timeDifference = ChronoUnit.DAYS.between(
                installmentList.get(installmentList.size()-1).getInstallmentDate(),
                dateSchedule.getDateFor(scheduleConfiguration.getInstallmentAmount())
                );

        double interestInstallment = scheduleConfiguration.getInterestRate()
                        * installmentList.get(installmentList.size()-1).getRemainingDebt()
                        * (double) (timeDifference)
                        / dateSchedule.getDateFor(scheduleConfiguration.getInstallmentAmount()).lengthOfYear();

        installmentList.add(new Installment(
                        scheduleConfiguration.getInstallmentAmount(),
                        dateSchedule.getDateFor(scheduleConfiguration.getInstallmentAmount()),
                        scheduleConfiguration.getCapital() - capitalInstallmentSum,
                        interestInstallment,
                        0
                        ));

        return installmentList;

    }

}
