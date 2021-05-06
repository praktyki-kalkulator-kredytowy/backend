package com.praktyki.backend.web.controllers;

import com.praktyki.backend.services.schedule.Installment;
import com.praktyki.backend.services.schedule.InstallmentType;
import com.praktyki.backend.services.schedule.ScheduleConfiguration;
import com.praktyki.backend.services.schedule.ScheduleService;
import com.praktyki.backend.web.exception.ConfigurationNotFound;
import com.praktyki.backend.web.requestModels.ScheduleConfigurationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@CrossOrigin
@RestController
public class ScheduleController {

    @Autowired
    private ScheduleService mScheduleService;

    private ScheduleConfiguration mScheduleConfiguration;

    @PostMapping("/api/v1/schedule")
    public List<Installment> createScheduleConfiguration(@Valid @RequestBody ScheduleConfigurationModel scheduleConfigurationModel) {
        mScheduleConfiguration = convertToScheduleConfiguration(scheduleConfigurationModel);
        return mScheduleService.createSchedule(mScheduleConfiguration);
    }

    public ScheduleConfiguration convertToScheduleConfiguration(ScheduleConfigurationModel scheduleConfigurationModel){
        return new ScheduleConfiguration(
                BigDecimal.valueOf(scheduleConfigurationModel.capital),
                InstallmentType.valueOf(scheduleConfigurationModel.installmentType),
                scheduleConfigurationModel.installmentAmount,
                scheduleConfigurationModel.interestRate,
                new Date(scheduleConfigurationModel.withdrawalDate.getTime()).toLocalDate()
        );
    }

    @GetMapping("/api/v1/schedule/create")
    public List<Installment> createSchedule() throws ConfigurationNotFound {

        if(mScheduleConfiguration == null) throw new ConfigurationNotFound("Configuration not found");

        return mScheduleService.createSchedule(mScheduleConfiguration);
    }
}
