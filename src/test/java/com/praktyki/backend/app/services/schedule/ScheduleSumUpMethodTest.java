package com.praktyki.backend.app.services.schedule;

import com.praktyki.backend.business.entities.InstallmentType;
import com.praktyki.backend.business.services.schedule.ScheduleService;
import com.praktyki.backend.business.services.schedule.dates.MonthlyDateCalculator;
import com.praktyki.backend.business.value.ScheduleConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@SpringBootTest(classes = {
        ScheduleService.class,
        MonthlyDateCalculator.class
})
public class ScheduleSumUpMethodTest {

    @Autowired
    private ScheduleService mScheduleService;

    @Test
    public void testInterestInstallmentSumUp() {

        ScheduleConfiguration scheduleConfiguration = ScheduleConfiguration.builder()
                .setCapital(BigDecimal.valueOf(20000))
                .setInstallmentAmount(12)
                .setInstallmentType(InstallmentType.CONSTANT)
                .setInterestRate(0.1)
                .setCommissionRate(0.2)
                .setWithdrawalDate(LocalDate.of(2021,5,31))
                .build();

        Assertions.assertEquals(
                mScheduleService.sumUpInterestInstallment(
                        mScheduleService.createInstallmentSchedule(scheduleConfiguration)
                ),
                BigDecimal.valueOf(1101.58).setScale(2, RoundingMode.HALF_UP)
                );

        ScheduleConfiguration scheduleConfiguration2 = ScheduleConfiguration.builder()
                .setCapital(BigDecimal.valueOf(17000))
                .setInstallmentAmount(30)
                .setInstallmentType(InstallmentType.DECREASING)
                .setInterestRate(0.21)
                .setCommissionRate(0.2)
                .setWithdrawalDate(LocalDate.of(2019,4,3))
                .build();

        Assertions.assertEquals(
                mScheduleService.sumUpInterestInstallment(
                        mScheduleService.createInstallmentSchedule(scheduleConfiguration2)
                ),
                BigDecimal.valueOf(4616.33).setScale(2, RoundingMode.HALF_UP)
        );

    }

}
