package com.praktyki.backend.business;

import com.praktyki.backend.app.configuration.ConfigurationImpl;
import com.praktyki.backend.app.data.repositories.ConfigurationRepository;
import com.praktyki.backend.business.entities.InstallmentType;
import com.praktyki.backend.business.entities.dates.ConfiguredDateScheduleCalculator;
import com.praktyki.backend.business.entities.dates.MonthlyDateScheduleCalculator;
import com.praktyki.backend.business.entities.dates.QuarterlyDateScheduleCalculator;
import com.praktyki.backend.business.services.InstallmentScheduleService;
import com.praktyki.backend.business.services.InsuranceService;
import com.praktyki.backend.business.utils.MathUtils;
import com.praktyki.backend.business.value.Installment;
import com.praktyki.backend.business.value.InsurancePremium;
import com.praktyki.backend.business.value.ScheduleConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootTest( classes = {
        InstallmentScheduleService.class,
        InsuranceService.class,
        MonthlyDateScheduleCalculator.class,
        QuarterlyDateScheduleCalculator.class,
        ConfiguredDateScheduleCalculator.class,
        ConfigurationImpl.class
})
public class InsuranceServiceTests {

    @MockBean
    private ConfigurationRepository mConfigurationRepository;

    @Autowired
    private InstallmentScheduleService mInstallmentScheduleService;

    @Autowired
    private InsuranceService mInsuranceService;

    @Test
    public void testInsuranceService() {

        ScheduleConfiguration conf = ScheduleConfiguration.builder()
                .setInstallmentType(InstallmentType.CONSTANT)
                .setInterestRate(0.1)
                .setCapital(BigDecimal.valueOf(10000))
                .setInstallmentAmount(12)
                .setCommissionRate(0.05)
                .setInsuranceRate(0.1)
                .setWithdrawalDate(LocalDate.of(2021, 4, 11))
                .build();

            List<Installment> installments = mInstallmentScheduleService.createInstallmentSchedule(conf);

        List<InsurancePremium> expected = Arrays.asList(
                new InsurancePremium(1, LocalDate.of(2021, 5, 11), BigDecimal.valueOf(250)
                        .setScale(2, RoundingMode.HALF_UP)),
                new InsurancePremium(2, LocalDate.of(2021, 8, 11), BigDecimal.valueOf(250)
                        .setScale(2, RoundingMode.HALF_UP)),
                new InsurancePremium(3, LocalDate.of(2021, 11, 11), BigDecimal.valueOf(250)
                        .setScale(2, RoundingMode.HALF_UP)),
                new InsurancePremium(4, LocalDate.of(2022, 2, 11), BigDecimal.valueOf(250)
                        .setScale(2, RoundingMode.HALF_UP))
        );

        List<InsurancePremium> actual = mInsuranceService.calculateInsurancePremium(conf, installments);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void testInsuranceServiceMinValue() {
        ScheduleConfiguration conf = ScheduleConfiguration.builder()
                .setInstallmentType(InstallmentType.CONSTANT)
                .setInterestRate(0.1)
                .setCapital(BigDecimal.valueOf(100))
                .setInstallmentAmount(12)
                .setCommissionRate(0.05)
                .setInsuranceRate(0.1)
                .setWithdrawalDate(LocalDate.of(2021, 4, 11))
                .build();


        List<InsurancePremium> expected = Arrays.asList(
                new InsurancePremium(1, LocalDate.of(2021, 5, 11),
                        new BigDecimal("10").setScale(2, RoundingMode.HALF_UP)),
                new InsurancePremium(2, LocalDate.of(2021, 8, 11),
                        new BigDecimal("10").setScale(2, RoundingMode.HALF_UP)),
                new InsurancePremium(3, LocalDate.of(2021, 11, 11),
                        new BigDecimal("10").setScale(2, RoundingMode.HALF_UP)),
                new InsurancePremium(4, LocalDate.of(2022, 2, 11),
                        new BigDecimal("10").setScale(2, RoundingMode.HALF_UP))
        );

        List<Installment> installments = mInstallmentScheduleService.createInstallmentSchedule(conf);

        List<InsurancePremium> actual = mInsuranceService.calculateInsurancePremium(conf, installments);

        Assertions.assertEquals(expected, actual);


    }


}
