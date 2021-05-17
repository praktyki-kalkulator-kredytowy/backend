package com.praktyki.backend.business;

import com.praktyki.backend.app.configuration.ConfigurationImpl;
import com.praktyki.backend.app.data.repositories.ConfigurationRepository;
import com.praktyki.backend.app.mocks.data.repositories.MockupConfigurationRepositoryImpl;
import com.praktyki.backend.business.entities.InstallmentType;
import com.praktyki.backend.business.entities.dates.MonthlyDateScheduleCalculator;
import com.praktyki.backend.business.services.InstallmentScheduleService;
import com.praktyki.backend.business.value.ScheduleConfiguration;
import com.praktyki.backend.app.configuration.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@SpringBootTest(classes = {
        InstallmentScheduleService.class,
        MonthlyDateScheduleCalculator.class,
        ConfigurationImpl.class,
        MockupConfigurationRepositoryImpl.class,
})
public class ScheduleSumUpMethodTest {

    @Autowired
    private InstallmentScheduleService mInstallmentScheduleService;

    @Autowired
    private Configuration mConfiguration;

    @Test
    public void testInterestInstallmentSumUp() {

        ScheduleConfiguration scheduleConfiguration = ScheduleConfiguration.builder()
                .setCapital(BigDecimal.valueOf(20000))
                .setInstallmentAmount(12)
                .setInstallmentType(InstallmentType.CONSTANT)
                .setInterestRate(0.1)
                .setCommissionRate(0.2)
                .setInsurance(false)
                .setWithdrawalDate(LocalDate.of(2021,5,31))
                .build();

        Assertions.assertEquals(
                mInstallmentScheduleService.sumUpInterestInstallment(
                        mInstallmentScheduleService.createInstallmentSchedule(scheduleConfiguration)
                ),
                BigDecimal.valueOf(1101.58).setScale(2, RoundingMode.HALF_UP)
                );

        ScheduleConfiguration scheduleConfiguration2 = ScheduleConfiguration.builder()
                .setCapital(BigDecimal.valueOf(17000))
                .setInstallmentAmount(30)
                .setInstallmentType(InstallmentType.DECREASING)
                .setInterestRate(0.21)
                .setCommissionRate(0.2)
                .setInsurance(false)
                .setWithdrawalDate(LocalDate.of(2019,4,3))
                .build();

        Assertions.assertEquals(
                mInstallmentScheduleService.sumUpInterestInstallment(
                        mInstallmentScheduleService.createInstallmentSchedule(scheduleConfiguration2)
                ),
                BigDecimal.valueOf(4616.33).setScale(2, RoundingMode.HALF_UP)
        );

    }

}
