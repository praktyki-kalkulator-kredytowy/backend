package com.praktyki.backend.web.controllers;

import com.praktyki.backend.app.interactors.ScheduleInteractor;
import com.praktyki.backend.business.services.exceptions.NoInsuranceRateForAgeException;
import com.praktyki.backend.business.value.Schedule;
import com.praktyki.backend.web.models.ScheduleModel;
import com.praktyki.backend.web.models.converters.ScheduleConfigurationConverter;
import com.praktyki.backend.web.models.ScheduleConfigurationModel;
import com.praktyki.backend.web.models.converters.ScheduleConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin
@RestController
public class ScheduleController {

    @Autowired
    private ScheduleInteractor mScheduleInteractor;

    @Autowired
    private ScheduleConfigurationConverter mScheduleConfigurationConverter;

    @Autowired
    private ScheduleConverter mScheduleConverter;

    @PostMapping("/api/v1/schedule")
    public ScheduleModel createScheduleConfiguration(@Valid @RequestBody ScheduleConfigurationModel scheduleConfigurationModel)
            throws NoInsuranceRateForAgeException {

        Schedule schedule =  mScheduleInteractor.calculateSchedule(
                mScheduleConfigurationConverter.convertToScheduleConfiguration(scheduleConfigurationModel)
        );

        return mScheduleConverter.convertToModel(schedule);
    }


}
