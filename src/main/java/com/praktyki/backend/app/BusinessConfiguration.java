package com.praktyki.backend.app;

import com.praktyki.backend.business.entities.dates.QuarterlyDateScheduleCalculator;
import com.praktyki.backend.business.services.StringService;
import com.praktyki.backend.business.services.schedule.ScheduleService;
import com.praktyki.backend.business.entities.dates.DateScheduleCalculator;
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
    public ScheduleService getScheduleService(MonthlyDateScheduleCalculator calculator) {
        return new ScheduleService(calculator);
    }

    @Bean
    @Scope("singleton")
    public StringService getStringService() {
        return new StringService();
    }
}
