package com.praktyki.backend.web.controllers;

import com.praktyki.backend.app.interactors.ScheduleInteractor;
import com.praktyki.backend.business.entities.InstallmentType;
import com.praktyki.backend.business.services.exceptions.NoInsuranceRateForAgeException;
import com.praktyki.backend.business.value.Schedule;
import com.praktyki.backend.business.value.ScheduleConfiguration;
import com.praktyki.backend.web.request.models.ScheduleConfigurationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.sql.Date;

@CrossOrigin
@RestController
public class ScheduleController {

    @Autowired
    private ScheduleInteractor mScheduleInteractor;

    @PostMapping("/api/v1/schedule")
    public Schedule createScheduleConfiguration(@Valid @RequestBody ScheduleConfigurationModel scheduleConfigurationModel) throws NoInsuranceRateForAgeException {
        return mScheduleInteractor.calculateSchedule(convertToScheduleConfiguration(scheduleConfigurationModel));
    }

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
