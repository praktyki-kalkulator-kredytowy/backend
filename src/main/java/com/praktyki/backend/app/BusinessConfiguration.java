package com.praktyki.backend.app;

import com.praktyki.backend.business.entities.dates.CustomDateScheduleCalculator;
import com.praktyki.backend.business.entities.dates.QuarterlyDateScheduleCalculator;
import com.praktyki.backend.business.services.APRCService;
import com.praktyki.backend.business.services.InsuranceService;
import com.praktyki.backend.business.services.StringService;
import com.praktyki.backend.business.services.InstallmentScheduleService;
import com.praktyki.backend.business.entities.dates.MonthlyDateScheduleCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.Configuration;

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
            CustomDateScheduleCalculator calculator,
            com.praktyki.backend.configuration.Configuration configuration) {
        return new InsuranceService(calculator, configuration);
    }

    @Bean
    @Scope("singleton")
    public APRCService getAPRCService() {
        return new APRCService();
    }
}
