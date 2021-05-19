package com.praktyki.backend.web.controllers;

import com.praktyki.backend.app.interactors.ScheduleInteractor;
import com.praktyki.backend.business.services.exceptions.NoInsuranceRateForAgeException;
import com.praktyki.backend.business.value.Schedule;
import com.praktyki.backend.business.value.ScheduleConfiguration;
import com.praktyki.backend.web.models.converters.ScheduleConfigurationConverter;
import com.praktyki.backend.web.models.request.ScheduleConfigurationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@CrossOrigin
@RestController
public class PdfController {

    @Autowired
    private ScheduleConfigurationConverter mScheduleConfigurationConverter;

    @Autowired
    private ScheduleInteractor mScheduleInteractor;

    @GetMapping("/api/v1/schedule/pdf")
    public void transportPdfToFrontend(
            HttpServletResponse response,
            @Valid @RequestBody ScheduleConfigurationModel scheduleConfigurationModel
    ) throws IOException, NoInsuranceRateForAgeException {

        ScheduleConfiguration scheduleConfiguration =
                mScheduleConfigurationConverter.convertToScheduleConfiguration(scheduleConfigurationModel);

        Schedule schedule = mScheduleInteractor.calculateSchedule(scheduleConfiguration);

        response.setContentType(MediaType.APPLICATION_PDF.toString());

        mScheduleInteractor.generatePdf(schedule, response.getOutputStream());

        response.getOutputStream().flush();
    }

}
