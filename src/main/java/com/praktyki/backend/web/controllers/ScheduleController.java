package com.praktyki.backend.web.controllers;

import com.praktyki.backend.app.interactors.ScheduleInteractor;
import com.praktyki.backend.business.value.Installment;
import com.praktyki.backend.business.entities.InstallmentType;
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
import java.util.List;

@CrossOrigin
@RestController
public class ScheduleController {

    @Autowired
    private ScheduleInteractor mScheduleInteractor;

    @PostMapping("/api/v1/schedule")
    public List<Installment> createScheduleConfiguration(@Valid @RequestBody ScheduleConfigurationModel scheduleConfigurationModel) {
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
                scheduleConfigurationModel.insuranceRate
        );
    }
}
