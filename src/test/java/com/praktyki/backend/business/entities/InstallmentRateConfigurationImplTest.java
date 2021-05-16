package com.praktyki.backend.business.entities;

import com.praktyki.backend.app.configuration.ConfigurationGroupKeys;
import com.praktyki.backend.app.configuration.ConfigurationImpl;
import com.praktyki.backend.app.data.repositories.ConfigurationRepository;
import com.praktyki.backend.business.services.exceptions.NoInsuranceRateForAgeException;
import com.praktyki.backend.configuration.Configuration;
import com.praktyki.backend.configuration.ConfigurationGroupKey;
import com.praktyki.backend.configuration.exceptions.ConfigurationValueValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {
        ConfigurationImpl.class,
})
class InstallmentRateConfigurationImplTest {

    @MockBean
    private ConfigurationRepository mConfigurationRepository;

    @Autowired
    private Configuration mConfiguration;

    @Test
    void getRateForAge() throws ConfigurationValueValidationException {
        InstallmentRateConfiguration rateConfiguration = new InstallmentRateConfigurationImpl(mConfiguration);

        ConfigurationGroupKey group = ConfigurationGroupKeys.INSURANCE_GROUPS;
        mConfiguration.getGroup(group)
                .save(group.createKey("5"), "0.1")
                .save(group.createKey("20"), "0.2")
                .save(group.createKey("40"), "0.4")
                .save(group.createKey("70"), "0.7");


        Assertions.assertDoesNotThrow(() -> {

            Assertions.assertEquals(0.1, rateConfiguration.getRateForAge(18));

            Assertions.assertEquals(0.2, rateConfiguration.getRateForAge(25));

            Assertions.assertEquals(0.4, rateConfiguration.getRateForAge(40));

            Assertions.assertEquals(0.7, rateConfiguration.getRateForAge(99));

        });

        Assertions.assertThrows(NoInsuranceRateForAgeException.class, () -> {
           rateConfiguration.getRateForAge(1);
        });
    }
}