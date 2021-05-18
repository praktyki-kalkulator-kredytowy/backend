package com.praktyki.backend.web.controllers;

import com.praktyki.backend.business.services.PdfService;
import com.praktyki.backend.business.value.Schedule;
import com.praktyki.backend.web.models.converters.ScheduleConfigurationConverter;
import com.praktyki.backend.web.models.request.ScheduleConfigurationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@CrossOrigin
@RestController
public class PdfController {

    @Autowired
    private PdfService mPdfService;

    @Autowired
    private ScheduleConfigurationConverter mScheduleConfigurationConverter;

    @GetMapping("/api/v1/schedule/pdf")
    public void transportPdfToFrontend(
            HttpServletResponse response,
            @Valid @RequestBody ScheduleConfigurationModel scheduleConfigurationModel
    ) throws IOException {

        Path outputFile = Paths.get(mPdfService.generatePdf(
                mScheduleConfigurationConverter.convertToScheduleConfiguration(scheduleConfigurationModel)
        ).getAbsoluteFile().toString());

        if(Files.exists(outputFile)){
            response.setContentType(MediaType.APPLICATION_PDF.toString());
            Files.copy(outputFile, response.getOutputStream());
            response.getOutputStream().flush();
        }

    }

}
