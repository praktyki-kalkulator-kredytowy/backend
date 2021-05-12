package com.praktyki.backend.app;

import com.praktyki.backend.business.entities.dates.CustomDateScheduleCalculator;
import com.praktyki.backend.business.entities.dates.QuarterlyDateScheduleCalculator;
import com.praktyki.backend.business.services.InsuranceService;
import com.praktyki.backend.business.services.StringService;
import com.praktyki.backend.business.services.InstallmentScheduleService;
import com.praktyki.backend.business.entities.dates.MonthlyDateScheduleCalculator;
import com.praktyki.backend.configuration.ConfigurationStore;
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
    public CustomDateScheduleCalculator getCustomDateScheduleCalculator() {
        return new CustomDateScheduleCalculator();
    }

    @Bean
    @Scope("singleton")
    public InstallmentScheduleService getScheduleService(
            MonthlyDateScheduleCalculator calculator,
            ConfigurationStore configurationStore) {
        return new InstallmentScheduleService(calculator, configurationStore );
    }

    @Bean
    @Scope("singleton")
    public StringService getStringService() {
        return new StringService();
    }

    @Bean
    @Scope("singleton")
    public InsuranceService getInsuranceService(
            CustomDateScheduleCalculator calculator,
            ConfigurationStore configurationStore) {
        return new InsuranceService(calculator, configurationStore);
    }
}
