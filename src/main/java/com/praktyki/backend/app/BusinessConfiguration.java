package com.praktyki.backend.app;

import com.praktyki.backend.business.services.StringService;
import com.praktyki.backend.business.services.schedule.ScheduleService;
import com.praktyki.backend.business.services.schedule.dates.DateScheduleCalculator;
import com.praktyki.backend.business.services.schedule.dates.MonthlyDateCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class BusinessConfiguration {

    @Bean
    @Scope("singleton")
    public MonthlyDateCalculator getMonthlyDateCalculator() {
        return new MonthlyDateCalculator();
    }

    @Bean
    @Scope("singleton")
    public ScheduleService getScheduleService(DateScheduleCalculator calculator) {
        return new ScheduleService(calculator);
    }

    @Bean
    @Scope("singleton")
    public StringService getStringService() {
        return new StringService();
    }
}
