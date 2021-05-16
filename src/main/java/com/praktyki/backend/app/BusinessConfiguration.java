package com.praktyki.backend.app;

import com.praktyki.backend.business.entities.InstallmentRateConfiguration;
import com.praktyki.backend.business.entities.InstallmentRateConfigurationImpl;
import com.praktyki.backend.business.entities.dates.ConfiguredDateScheduleCalculator;
import com.praktyki.backend.business.entities.dates.MonthlyDateScheduleCalculator;
import com.praktyki.backend.business.entities.dates.QuarterlyDateScheduleCalculator;
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
    public QuarterlyDateScheduleCalculator getQuarterlyDateScheduleCalculator() {
        return new QuarterlyDateScheduleCalculator();
    }

    @Bean
    @Scope("singleton")
    public ConfiguredDateScheduleCalculator getConfiguredDateScheduleCalculator() {
        return new ConfiguredDateScheduleCalculator();
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
            ConfiguredDateScheduleCalculator calculator,
            com.praktyki.backend.configuration.Configuration configuration,
            InstallmentRateConfiguration installmentRateConfiguration
    ) {
        return new InsuranceService(calculator, configuration, installmentRateConfiguration);
    }

    @Bean
    @Scope("singleton")
    public APRCService getAPRCService() {
        return new APRCService();
    }

    @Bean
    @Scope("singleton")
    public InstallmentRateConfiguration getInstallmentRateConfiguration(
            com.praktyki.backend.configuration.Configuration configuration
    ) {
        return new InstallmentRateConfigurationImpl(configuration);
    }
}
