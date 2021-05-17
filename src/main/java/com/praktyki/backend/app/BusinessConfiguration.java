package com.praktyki.backend.app;

import com.praktyki.backend.business.entities.InsuranceRateConfiguration;
import com.praktyki.backend.business.entities.InsuranceRateConfigurationImpl;
import com.praktyki.backend.business.entities.dates.InsurancePremiumDateCalculator;
import com.praktyki.backend.business.entities.dates.MonthlyDateScheduleCalculator;
import com.praktyki.backend.business.services.APRCService;
import com.praktyki.backend.business.services.InstallmentScheduleService;
import com.praktyki.backend.business.services.InsuranceService;
import com.praktyki.backend.business.services.StringService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class BusinessConfiguration {

    @Bean
    @Scope("singleton")
    public MonthlyDateScheduleCalculator getMonthlyDateCalculator() {
        return new MonthlyDateScheduleCalculator();
    }

    @Bean
    @Scope("singleton")
    public InsurancePremiumDateCalculator getConfiguredDateScheduleCalculator(
            com.praktyki.backend.configuration.Configuration configuration) {
        return new InsurancePremiumDateCalculator(configuration);
    }

    @Bean
    @Scope("singleton")
    public InstallmentScheduleService getScheduleService(
            MonthlyDateScheduleCalculator calculator,
            com.praktyki.backend.configuration.Configuration configuration) {
        return new InstallmentScheduleService(calculator, configuration );
    }

    @Bean
    @Scope("singleton")
    public StringService getStringService() {
        return new StringService();
    }

    @Bean
    @Scope("singleton")
    public InsuranceService getInsuranceService(
            InsurancePremiumDateCalculator calculator,
            com.praktyki.backend.configuration.Configuration configuration,
            InsuranceRateConfiguration insuranceRateConfiguration
    ) {
        return new InsuranceService(calculator, configuration, insuranceRateConfiguration);
    }

    @Bean
    @Scope("singleton")
    public APRCService getAPRCService() {
        return new APRCService();
    }

    @Bean
    @Scope("singleton")
    public InsuranceRateConfiguration getInstallmentRateConfiguration(
            com.praktyki.backend.configuration.Configuration configuration
    ) {
        return new InsuranceRateConfigurationImpl(configuration);
    }
}
