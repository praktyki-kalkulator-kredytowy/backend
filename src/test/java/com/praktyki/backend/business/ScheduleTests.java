package com.praktyki.backend.business;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.praktyki.backend.app.configuration.*;
import com.praktyki.backend.app.interactors.ScheduleInteractor;
import com.praktyki.backend.app.mocks.data.repositories.MockupConfigurationRepositoryImpl;
import com.praktyki.backend.business.entities.InsuranceRateConfigurationImpl;
import com.praktyki.backend.business.entities.dates.InsurancePremiumDateCalculator;
import com.praktyki.backend.business.entities.dates.MonthlyDateScheduleCalculator;
import com.praktyki.backend.business.services.APRCService;
import com.praktyki.backend.business.services.InstallmentScheduleService;
import com.praktyki.backend.business.services.InsuranceService;
import com.praktyki.backend.business.services.exceptions.NoInsuranceRateForAgeException;
import com.praktyki.backend.app.configuration.exceptions.ConfigurationValueValidationException;
import com.praktyki.backend.business.value.Schedule;
import com.praktyki.backend.configuration.Configuration;
import com.praktyki.backend.configuration.ConfigurationGroupKey;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@SpringBootTest(classes = {
        InstallmentScheduleService.class,
        MonthlyDateScheduleCalculator.class,
        ConfigurationImpl.class,
        MockupConfigurationRepositoryImpl.class,
        ScheduleInteractor.class,
        APRCService.class,
        InsuranceService.class,
        InsurancePremiumDateCalculator.class,
        InsuranceRateConfigurationImpl.class,
})
public class ScheduleTests {

    public static final BigDecimal  REQUIRED_APRC_ACCURRECY = BigDecimal.valueOf(0.002);

    @Autowired
    private InstallmentScheduleService mInstallmentScheduleService;

    @Autowired
    private Configuration mConfiguration;

    @Autowired
    private ScheduleInteractor mScheduleInteractor;


    private ObjectMapper mObjectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
            .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

    public void repositorySetUp() throws ConfigurationValueValidationException {
        ConfigurationGroupKey group = ConfigurationGroupKeys.INSURANCE_GROUPS;
        mConfiguration.getGroup(group)
                .save(group.createKey("0"), "0.0")
                .save(group.createKey("30"), "0.002")
                .save(group.createKey("50"), "0.005")
                .save(group.createKey("65"), "0.009");
    }

    @Test
    public void test() throws IOException, ConfigurationValueValidationException {

        repositorySetUp();

        List<ScheduleTestCase> testCases = mObjectMapper
                .readValue(
                        new File("src/test/resources/ScheduleTests.json"),
                        new TypeReference<List<ScheduleTestCase>>() {}
                );

        Assertions.assertDoesNotThrow(() -> {
            for(ScheduleTestCase testCase : testCases) testSingleTestCase(testCase);
        });

        mConfiguration.getGroup(ConfigurationGroupKeys.INSURANCE_GROUPS).remove(
                ConfigurationGroupKeys.INSURANCE_GROUPS.createKey("0")
        );
    }

    public void testSingleTestCase(ScheduleTestCase testCase) throws NoInsuranceRateForAgeException, ConfigurationValueValidationException {
        mConfiguration.save(
                        ConfigurationKeys.MONTH_FRAME,
                        String.valueOf(testCase.getTestScheduleConfiguration().getMonthFrame())
                );

        Schedule schedule = mScheduleInteractor.calculateSchedule(
                testCase.getTestScheduleConfiguration().toScheduleConfiguration()
                );

        Assertions.assertEquals(testCase.getInstallmentList().size(), schedule.getInstallmentList().size());
        for(int i = 0; i < schedule.getInstallmentList().size(); i++) {
            Assertions.assertEquals(testCase.getInstallmentList().get(i), schedule.getInstallmentList().get(i));
        }

        Assertions.assertEquals(testCase.getInsurancePremiumList().size(), schedule.getInsurancePremiumList().size());
        for(int i = 0; i < schedule.getInsurancePremiumList().size(); i++) {
            Assertions.assertEquals(testCase.getInsurancePremiumList().get(i), schedule.getInsurancePremiumList().get(i));
        }

        Assertions.assertTrue(testCase
                .getAPRC()
                .subtract(schedule.getAPRC())
                .abs()
                .compareTo(REQUIRED_APRC_ACCURRECY)
                <= 0
        );

        Assertions.assertEquals(testCase.getInsuranceTotalAmount(), schedule.getInsuranceTotalAmount());
        Assertions.assertEquals(testCase.getCommissionAmount(), schedule.getCommissionAmount());
        Assertions.assertEquals(testCase.getLoanPaidOutAmount(), schedule.getLoanPaidOutAmount());
        Assertions.assertEquals(testCase.getLoanTotalCost(), schedule.getLoanTotalCost());
        Assertions.assertEquals(testCase.getSumUpCapitalInstallment(), schedule.getSumUpCapitalInstallment());


    }

}
