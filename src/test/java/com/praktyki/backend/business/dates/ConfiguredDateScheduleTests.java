package com.praktyki.backend.business.dates;

import com.praktyki.backend.app.configuration.ConfigurationImpl;
import com.praktyki.backend.app.configuration.ConfigurationKeys;
import com.praktyki.backend.app.mocks.data.repositories.MockupConfigurationRepositoryImpl;
import com.praktyki.backend.business.entities.dates.DateSchedule;
import com.praktyki.backend.business.entities.dates.InsurancePremiumDateCalculator;
import com.praktyki.backend.configuration.Configuration;
import com.praktyki.backend.app.configuration.exceptions.ConfigurationValueValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootTest(classes = {
        ConfigurationImpl.class,
        InsurancePremiumDateCalculator.class,
        MockupConfigurationRepositoryImpl.class,
})
public class ConfiguredDateScheduleTests {

    @Autowired
    private Configuration mConfiguration;

    @Autowired
    private InsurancePremiumDateCalculator mInsurancePremiumDateCalculator;

    private final List<LocalDate> expectedThreeMonth = Arrays.asList(
            LocalDate.of(2021, 5, 31),
            LocalDate.of(2021, 8, 31),
            LocalDate.of(2021, 11, 30),
            LocalDate.of(2022, 2, 28),
            LocalDate.of(2022, 5, 31),
            LocalDate.of(2022, 8, 31)

    );

    private final List<LocalDate> expectedFourMonths = Arrays.asList(
            LocalDate.of(2021, 5, 31),
            LocalDate.of(2021, 9, 30),
            LocalDate.of(2022, 1, 31),
            LocalDate.of(2022, 5, 31),
            LocalDate.of(2022, 9, 30),
            LocalDate.of(2023, 1, 31)

    );

    private final List<LocalDate> expectedTwoMonth = Arrays.asList(
            LocalDate.of(2021, 5, 31),
            LocalDate.of(2021, 7, 31),
            LocalDate.of(2021, 9, 30),
            LocalDate.of(2021, 11, 30),
            LocalDate.of(2022, 1, 31),
            LocalDate.of(2022, 3, 31)

    );

    public void repositorySetUp() throws ConfigurationValueValidationException {

        mConfiguration.save(ConfigurationKeys.MONTH_FRAME,
                ConfigurationKeys.MONTH_FRAME.getDefaultValue());

    }

    @Test
    public void testCustomDateSchedule() throws ConfigurationValueValidationException {

        repositorySetUp();


        DateSchedule schedule = mInsurancePremiumDateCalculator.calculate(LocalDate.of(2021, 5, 31));

        for(int i = 1; i <= expectedThreeMonth.size(); i++)
            Assertions.assertEquals(expectedThreeMonth.get(i - 1), schedule.getDateFor(i));

        mConfiguration.save(ConfigurationKeys.MONTH_FRAME, "4");


        schedule = mInsurancePremiumDateCalculator.calculate(LocalDate.of(2021, 5, 31));

        for(int i = 1; i <= expectedFourMonths.size(); i++)
            Assertions.assertEquals(expectedFourMonths.get(i - 1), schedule.getDateFor(i));

        mConfiguration.save(ConfigurationKeys.MONTH_FRAME, "2");

        schedule = mInsurancePremiumDateCalculator.calculate(LocalDate.of(2021, 5, 31));

        for(int i = 1; i <= expectedTwoMonth.size(); i++)
            Assertions.assertEquals(expectedTwoMonth.get(i - 1), schedule.getDateFor(i));

    }

    @Test
    public void testCustomDateScheduleWithShift() throws ConfigurationValueValidationException {

        repositorySetUp();

        DateSchedule schedule = mInsurancePremiumDateCalculator.calculate(LocalDate.of(2021, 5, 31)).shift(1);

        for(int i = 1; i <= expectedThreeMonth.size() - 1; i++)
            Assertions.assertEquals(expectedThreeMonth.get(i), schedule.getDateFor(i));

        mConfiguration.save(ConfigurationKeys.MONTH_FRAME, "4");

        schedule = mInsurancePremiumDateCalculator.calculate(LocalDate.of(2021, 5, 31)).shift(1);

        for(int i = 1; i <= expectedFourMonths.size() - 1; i++)
            Assertions.assertEquals(expectedFourMonths.get(i), schedule.getDateFor(i));

        mConfiguration.save(ConfigurationKeys.MONTH_FRAME, "2");

        schedule = mInsurancePremiumDateCalculator.calculate(LocalDate.of(2021, 5, 31)).shift(1);

        for(int i = 1; i <= expectedTwoMonth.size() - 1; i++)
            Assertions.assertEquals(expectedTwoMonth.get(i), schedule.getDateFor(i));

        mConfiguration.save(ConfigurationKeys.MONTH_FRAME, "3");
    }
}
