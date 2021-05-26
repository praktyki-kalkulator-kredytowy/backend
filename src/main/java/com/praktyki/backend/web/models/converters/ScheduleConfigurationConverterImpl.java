package com.praktyki.backend.web.models.converters;

import com.praktyki.backend.business.entities.InstallmentType;
import com.praktyki.backend.business.value.ScheduleConfiguration;
import com.praktyki.backend.web.models.ScheduleConfigurationModel;
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
                scheduleConfigurationModel.withdrawalDate,
                scheduleConfigurationModel.commissionRate,
                scheduleConfigurationModel.insurance,
                scheduleConfigurationModel.age
        );
    }

    @Override
    public ScheduleConfigurationModel convertToScheduleConfigurationModel(ScheduleConfiguration c) {
        return new ScheduleConfigurationModel(
                c.getCapital().doubleValue(),
                c.getInstallmentType().toString(),
                c.getInstallmentAmount(),
                c.getInterestRate(),
                c.getWithdrawalDate(),
                c.getCommissionRate(),
                c.getAge(),
                c.isInsurance()
        );
    }
}
