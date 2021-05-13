package com.praktyki.backend.app;

import com.praktyki.backend.business.entities.dates.QuarterlyDateScheduleCalculator;
import com.praktyki.backend.business.services.APRCService;
import com.praktyki.backend.business.services.InsuranceService;
import com.praktyki.backend.business.services.StringService;
import com.praktyki.backend.business.services.InstallmentScheduleService;
import com.praktyki.backend.business.entities.dates.MonthlyDateScheduleCalculator;
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
    public InstallmentScheduleService getScheduleService(MonthlyDateScheduleCalculator calculator) {
        return new InstallmentScheduleService(calculator);
    }

    @Bean
    @Scope("singleton")
    public StringService getStringService() {
        return new StringService();
    }

    @Bean
    @Scope("singleton")
    public InsuranceService getInsuranceService(QuarterlyDateScheduleCalculator calculator) {
        return new InsuranceService(calculator);
    }

    @Bean
    @Scope("singleton")
    public APRCService getAPRCService() {
        return new APRCService();
    }
}
