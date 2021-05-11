package com.praktyki.backend.app.services.schedule;

import com.praktyki.backend.business.entities.InstallmentType;
import com.praktyki.backend.business.entities.dates.MonthlyDateScheduleCalculator;
import com.praktyki.backend.business.services.InstallmentScheduleService;
import com.praktyki.backend.business.value.ScheduleConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@SpringBootTest(classes = {
        InstallmentScheduleService.class,
        MonthlyDateScheduleCalculator.class
})
public class ScheduleCommissionCalculateTest {

    @Autowired
    private InstallmentScheduleService mInstallmentScheduleService;

    @Test
    public void testCommissionCalculate() {



        ScheduleConfiguration scheduleConfiguration = ScheduleConfiguration.builder()
                .setCapital(BigDecimal.valueOf(20000))
                .setInstallmentAmount(12)
                .setInstallmentType(InstallmentType.CONSTANT)
                .setInterestRate(0.1)
                .setCommissionRate(0.2)
                .setWithdrawalDate(LocalDate.now())
                .build();

        Assertions.assertEquals(
                mInstallmentScheduleService.calculateCommission(scheduleConfiguration),
                BigDecimal.valueOf(4000).setScale(2, RoundingMode.HALF_UP)
        );

        ScheduleConfiguration scheduleConfiguration2 = ScheduleConfiguration.builder()
                .setCapital(BigDecimal.valueOf(1000))
                .setInstallmentAmount(12)
                .setInstallmentType(InstallmentType.CONSTANT)
                .setInterestRate(0.1)
                .setCommissionRate(0.01)
                .setWithdrawalDate(LocalDate.now())
                .build();

        Assertions.assertEquals(
                mInstallmentScheduleService.calculateCommission(scheduleConfiguration2),
                BigDecimal.valueOf(50).setScale(2, RoundingMode.HALF_UP)
        );

        ScheduleConfiguration scheduleConfiguration3 = ScheduleConfiguration.builder()
                .setCapital(BigDecimal.valueOf(800000.68))
                .setInstallmentAmount(12)
                .setInstallmentType(InstallmentType.CONSTANT)
                .setInterestRate(0.1)
                .setCommissionRate(0.16)
                .setWithdrawalDate(LocalDate.now())
                .build();

        Assertions.assertEquals(
                mInstallmentScheduleService.calculateCommission(scheduleConfiguration3),
                BigDecimal.valueOf(128000.1088).setScale(2, RoundingMode.HALF_UP)
        );

    }

}
