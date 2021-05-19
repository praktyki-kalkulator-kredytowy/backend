package com.praktyki.backend.app.interactors;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.praktyki.backend.business.services.APRCService;
import com.praktyki.backend.business.services.InstallmentScheduleService;
import com.praktyki.backend.business.services.InsuranceService;
import com.praktyki.backend.business.services.exceptions.NoInsuranceRateForAgeException;
import com.praktyki.backend.business.value.Installment;
import com.praktyki.backend.business.value.InsurancePremium;
import com.praktyki.backend.business.value.Schedule;
import com.praktyki.backend.business.value.ScheduleConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

@Service
public class ScheduleInteractor {

    @Autowired
    private InstallmentScheduleService mInstallmentScheduleService;

    @Autowired
    private InsuranceService mInsuranceService;

    @Autowired
    private APRCService mAPRCService;

    @Autowired
    private ITemplateEngine mITemplateEngine;

    public Schedule calculateSchedule(ScheduleConfiguration configuration) throws NoInsuranceRateForAgeException {
        return createSchedule(configuration);
    }

    public Schedule createSchedule(ScheduleConfiguration scheduleConfiguration) throws NoInsuranceRateForAgeException {

        List<Installment> installments = mInstallmentScheduleService.createInstallmentSchedule(scheduleConfiguration);
        List<InsurancePremium> insurancePremiumList = scheduleConfiguration.isInsurance()
                ? mInsuranceService.calculateInsurancePremium(scheduleConfiguration, installments)
                : Collections.emptyList();
        BigDecimal commission = mInstallmentScheduleService.calculateCommission(scheduleConfiguration);
        BigDecimal sumUpInsurancePremium = mInsuranceService.calculateTotalInsuranceCost(insurancePremiumList);
        BigDecimal sumUpInterestInstallment = mInstallmentScheduleService.sumUpInterestInstallment(installments);

        return new Schedule(
                scheduleConfiguration,
                installments,
                insurancePremiumList,
                mInstallmentScheduleService.sumUpCapitalInstallment(installments),
                scheduleConfiguration.getCapital().subtract(commission),
                commission,
                sumUpInsurancePremium,
                commission.add(sumUpInsurancePremium).add(sumUpInterestInstallment),
                mAPRCService.calculateAPRC(scheduleConfiguration, installments,
                        insurancePremiumList, commission)
        );

    }

    public void generatePdf(Schedule schedule, OutputStream outputStream) throws IOException {
        Context context = new Context();
        context.setVariable("schedule", schedule);
        context.setVariable("conf", schedule.getScheduleConfiguration());
        String url;

        try {
            url = Paths.get("src/main/resources").toUri().toURL().toString();
        } catch (Exception e) {
            throw new IllegalStateException("Invalid resources path", e);
        }

        new PdfRendererBuilder()
                .withHtmlContent(
                        mITemplateEngine.process("schedule_template",context),
                        url
                )
                .toStream(outputStream)
                .run();


    }

}
