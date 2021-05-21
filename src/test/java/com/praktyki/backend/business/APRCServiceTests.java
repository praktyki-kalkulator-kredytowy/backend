package com.praktyki.backend.business;

import com.praktyki.backend.app.configuration.ConfigurationGroupKeys;
import com.praktyki.backend.app.configuration.ConfigurationImpl;
import com.praktyki.backend.app.configuration.ConfigurationKeys;
import com.praktyki.backend.app.configuration.exceptions.ConfigurationValueValidationException;
import com.praktyki.backend.app.mocks.data.repositories.MockupConfigurationRepositoryImpl;
import com.praktyki.backend.business.entities.InstallmentType;
import com.praktyki.backend.business.entities.InsuranceRateConfigurationImpl;
import com.praktyki.backend.business.entities.dates.InsurancePremiumDateCalculator;
import com.praktyki.backend.business.entities.dates.MonthlyDateScheduleCalculator;
import com.praktyki.backend.business.services.APRCService;
import com.praktyki.backend.business.services.InstallmentScheduleService;
import com.praktyki.backend.business.services.InsuranceService;
import com.praktyki.backend.business.services.exceptions.NoInsuranceRateForAgeException;
import com.praktyki.backend.business.value.Installment;
import com.praktyki.backend.business.value.InsurancePremium;
import com.praktyki.backend.business.value.ScheduleConfiguration;
import com.praktyki.backend.configuration.Configuration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest( classes = {
        InstallmentScheduleService.class,
        InsuranceService.class,
        MonthlyDateScheduleCalculator.class,
        InsurancePremiumDateCalculator.class,
        APRCService.class,
        ConfigurationImpl.class,
        InsuranceRateConfigurationImpl.class,
        MockupConfigurationRepositoryImpl.class,
})
public class APRCServiceTests {

    @Autowired
    private InstallmentScheduleService mInstallmentScheduleService;

    @Autowired
    private InsuranceService mInsuranceService;

    @Autowired
    private APRCService mAPRCService;

    @Autowired
    private Configuration mConfiguration;

    public void repositorySetUp() throws ConfigurationValueValidationException {

        mConfiguration.getGroup(ConfigurationGroupKeys.INSURANCE_GROUPS)
                .save(ConfigurationGroupKeys.INSURANCE_GROUPS.createKey("0"), "0.1");

        mConfiguration.save(ConfigurationKeys.MIN_PREMIUM_VALUE,
                ConfigurationKeys.MIN_PREMIUM_VALUE.getDefaultValue());

        mConfiguration.save(ConfigurationKeys.MONTH_FRAME,
                ConfigurationKeys.MONTH_FRAME.getDefaultValue());

    }
}
