package com.praktyki.backend.web.models.converters;

import com.praktyki.backend.business.entities.InstallmentType;
import com.praktyki.backend.business.value.ScheduleConfiguration;
import com.praktyki.backend.web.models.request.ScheduleConfigurationModel;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Date;

@Component
public class ScheduleConfigurationConverterImpl implements ScheduleConfigurationConverter {

    @Override
    public ScheduleConfiguration convertToScheduleConfiguration(ScheduleConfigurationModel scheduleConfigurationModel){
        return new ScheduleConfiguration(
                BigDecimal.valueOf(scheduleConfigurationModel.capital),
                InstallmentType.valueOf(scheduleConfigurationModel.installmentType),
                scheduleConfigurationModel.installmentAmount,
                scheduleConfigurationModel.interestRate,
                new Date(scheduleConfigurationModel.withdrawalDate.getTime()).toLocalDate(),
                scheduleConfigurationModel.commissionRate,
                scheduleConfigurationModel.insurance,
                scheduleConfigurationModel.age
        );
    }
}
