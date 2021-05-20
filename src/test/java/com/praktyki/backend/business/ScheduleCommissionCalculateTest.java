package com.praktyki.backend.business;

import com.praktyki.backend.app.configuration.ConfigurationImpl;
import com.praktyki.backend.app.configuration.ConfigurationKeys;
import com.praktyki.backend.app.mocks.data.repositories.MockupConfigurationRepositoryImpl;
import com.praktyki.backend.business.entities.InstallmentType;
import com.praktyki.backend.business.entities.dates.MonthlyDateScheduleCalculator;
import com.praktyki.backend.business.services.InstallmentScheduleService;
import com.praktyki.backend.business.value.ScheduleConfiguration;
import com.praktyki.backend.configuration.Configuration;
import com.praktyki.backend.app.configuration.exceptions.ConfigurationValueValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@SpringBootTest(classes = {
        InstallmentScheduleService.class,
        MonthlyDateScheduleCalculator.class,
        ConfigurationImpl.class,
        MockupConfigurationRepositoryImpl.class,
})
public class ScheduleCommissionCalculateTest {

    @Autowired
    private InstallmentScheduleService mInstallmentScheduleService;

    @Autowired
    private Configuration mConfiguration;

    public void repositorySetUp() throws ConfigurationValueValidationException {

        mConfiguration.save(ConfigurationKeys.MIN_COMMISSION_AMOUNT,
                ConfigurationKeys.MIN_COMMISSION_AMOUNT.getDefaultValue());

    }

    @Test
    public void testCommissionCalculate() throws ConfigurationValueValidationException {

        repositorySetUp();

        ScheduleConfiguration scheduleConfiguration = ScheduleConfiguration.builder()
                .setCapital(BigDecimal.valueOf(20000))
                .setInstallmentAmount(12)
                .setInstallmentType(InstallmentType.CONSTANT)
                .setInterestRate(0.1)
                .setCommissionRate(0.2)
                .setInsurance(false)
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
                .setInsurance(false)
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
                .setInsurance(false)
                .setWithdrawalDate(LocalDate.now())
                .build();

        Assertions.assertEquals(
                mInstallmentScheduleService.calculateCommission(scheduleConfiguration3),
                BigDecimal.valueOf(128000.1088).setScale(2, RoundingMode.HALF_UP)
        );

    }

}
