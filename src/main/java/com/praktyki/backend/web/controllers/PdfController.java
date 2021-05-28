package com.praktyki.backend.web.controllers;

import com.praktyki.backend.app.interactors.ScheduleInteractor;
import com.praktyki.backend.business.services.exceptions.NoInsuranceRateForAgeException;
import com.praktyki.backend.business.value.Schedule;
import com.praktyki.backend.business.value.ScheduleConfiguration;
import com.praktyki.backend.web.models.ScheduleModel;
import com.praktyki.backend.web.models.converters.ScheduleConfigurationConverter;
import com.praktyki.backend.web.models.ScheduleConfigurationModel;
import com.praktyki.backend.web.models.converters.ScheduleConverter;
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

    @Autowired
    private ScheduleConverter mScheduleConverter;

    @PostMapping("/api/v1/schedule/pdf")
    public void transportPdfToFrontend(
            HttpServletResponse response,
            @Valid @RequestBody ScheduleModel scheduleModel
    ) throws IOException, NoInsuranceRateForAgeException {

        response.setContentType(MediaType.APPLICATION_PDF.toString());

        mScheduleInteractor.generatePdf(scheduleModel, response.getOutputStream());

        response.getOutputStream().flush();
    }

}
