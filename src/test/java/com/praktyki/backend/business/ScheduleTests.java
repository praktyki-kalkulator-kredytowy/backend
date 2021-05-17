package com.praktyki.backend.business;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.praktyki.backend.app.configuration.ConfigurationGroupKeys;
import com.praktyki.backend.app.configuration.ConfigurationImpl;
import com.praktyki.backend.app.configuration.ConfigurationKeys;
import com.praktyki.backend.app.data.repositories.ConfigurationRepository;
import com.praktyki.backend.app.mocks.data.repositories.MockupConfigurationRepositoryImpl;
import com.praktyki.backend.business.entities.dates.MonthlyDateScheduleCalculator;
import com.praktyki.backend.business.services.InstallmentScheduleService;
import com.praktyki.backend.business.value.Installment;
import com.praktyki.backend.app.configuration.Configuration;
import com.praktyki.backend.app.configuration.exceptions.ConfigurationValueValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.File;
import java.io.IOException;
import java.util.List;

@SpringBootTest(classes = {
        InstallmentScheduleService.class,
        MonthlyDateScheduleCalculator.class,
        ConfigurationImpl.class,
        MockupConfigurationRepositoryImpl.class,
})
public class ScheduleTests {

    @Autowired
    private InstallmentScheduleService mInstallmentScheduleService;

    @Autowired
    private Configuration mConfiguration;


    private ObjectMapper mObjectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
            .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

    public void repositorySetUp() throws ConfigurationValueValidationException {

        mConfiguration.save(ConfigurationKeys.MIN_COMMISSION_AMOUNT,
                ConfigurationKeys.MIN_COMMISSION_AMOUNT.getDefaultValue());

        mConfiguration.getGroup(ConfigurationGroupKeys.INSURANCE_GROUPS)
                .save(ConfigurationGroupKeys.INSURANCE_GROUPS.createKey("0"), "0.1");

        mConfiguration.save(ConfigurationKeys.MONTH_FRAME,
                ConfigurationKeys.MONTH_FRAME.getDefaultValue());

        mConfiguration.save(ConfigurationKeys.MIN_PREMIUM_VALUE,
                ConfigurationKeys.MIN_PREMIUM_VALUE.getDefaultValue());

    }

    @Test
    public void test() throws IOException, ConfigurationValueValidationException {

        repositorySetUp();

        List<ScheduleTestCase> testCases = mObjectMapper
                .readValue(
                        new File("src/test/resources/ScheduleTests.json"),
                        new TypeReference<List<ScheduleTestCase>>() {}
                );

        testCases.forEach(this::testSingleTestCase);

        mConfiguration.getGroup(ConfigurationGroupKeys.INSURANCE_GROUPS).remove(
                ConfigurationGroupKeys.INSURANCE_GROUPS.createKey("0")
        );
    }

    public void testSingleTestCase(ScheduleTestCase testCase) {
        List<Installment> installments = mInstallmentScheduleService.createInstallmentSchedule(testCase.configuration);

        Assertions.assertEquals(installments.size(), testCase.installments.size());

        for(int i = 0; i < installments.size(); i++) {
            Assertions.assertEquals(testCase.installments.get(i), installments.get(i));
        }

    }

}
