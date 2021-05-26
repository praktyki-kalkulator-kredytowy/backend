package com.praktyki.backend.web.controllers;

import com.praktyki.backend.app.interactors.ScheduleInteractor;
import com.praktyki.backend.business.entities.InstallmentType;
import com.praktyki.backend.business.services.exceptions.NoInsuranceRateForAgeException;
import com.praktyki.backend.business.value.Schedule;
import com.praktyki.backend.business.value.ScheduleConfiguration;
import com.praktyki.backend.web.models.ScheduleCalculationEventDetailsModel;
import com.praktyki.backend.web.models.ScheduleCalculationEventModel;
import com.praktyki.backend.web.models.ScheduleConfigurationModel;
import com.praktyki.backend.web.models.response.ScheduleCalculationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@CrossOrigin
@RestController
public class AuditController {

    @Autowired
    private ScheduleInteractor mScheduleInteractor;

    private RestTemplate mRestTemplate;

    @Autowired
    private void configureTemplate(RestTemplateBuilder restTemplateBuilder) {
        mRestTemplate = restTemplateBuilder.build();
    }


    @GetMapping("/api/v1/audit")
    public List<ScheduleCalculationEventModel> audit(
            @RequestParam(value = "calculationStartDate", required = false) LocalDate calculationStartDate,
            @RequestParam(value = "calculationEndDate", required = false) LocalDate calculationEndDate,
            @RequestParam(value = "withdrawalStartDate", required = false) LocalDate withdrawalStartDate,
            @RequestParam(value = "withdrawalEndDate", required = false) LocalDate withdrawalEndDate,
            @RequestParam(value = "capitalStart", required = false) BigDecimal capitalStart,
            @RequestParam(value = "capitalEnd", required = false) BigDecimal capitalEnd,
            @RequestParam(value = "installmentAmountStart", required = false) BigDecimal installmentAmountStart,
            @RequestParam(value = "installmentAmountEnd", required = false) BigDecimal installmentAmountEnd,
            @RequestParam(value = "interestRateStart", required = false) Double interestRateStart,
            @RequestParam(value = "interestRateEnd", required = false) Double interestRateEnd,
            @RequestParam(value = "insuranceSumStart", required = false) BigDecimal insuranceSumStart,
            @RequestParam(value = "insuranceSumEnd", required = false) BigDecimal insuranceSumEnd,
            @RequestParam(value = "clientAgeStart", required = false) Integer clientAgeStart,
            @RequestParam(value = "clientAgeEnd", required = false) Integer clientAgeEnd,
            @RequestParam(value = "aprcStart", required = false) Double aprcStart,
            @RequestParam(value = "aprcEnd", required = false) Double aprcEnd,
            HttpServletRequest request
    ) throws NoInsuranceRateForAgeException {

        String url = "http://localhost:4201/api/v1/audit";

        if(request.getQueryString() != null) url += "?"+ request.getQueryString();

        return Arrays.asList(mRestTemplate.getForObject(url, ScheduleCalculationEventModel[].class));

    }

    @GetMapping("/api/v1/audit/{id}")
    public ScheduleCalculationEventDetailsModel getEventById(@PathVariable String id) {

        String url = "http://localhost:4201/api/v1/audit/" + id;
        return mRestTemplate.getForObject(url, ScheduleCalculationEventDetailsModel.class);

    }
}
