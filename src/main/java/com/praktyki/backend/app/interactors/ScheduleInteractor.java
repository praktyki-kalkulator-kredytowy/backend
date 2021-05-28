package com.praktyki.backend.app.interactors;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.praktyki.backend.app.RabbitMqConfiguration;
import com.praktyki.backend.business.services.APRCService;
import com.praktyki.backend.business.services.InstallmentScheduleService;
import com.praktyki.backend.business.services.InsuranceService;
import com.praktyki.backend.business.services.exceptions.NoInsuranceRateForAgeException;
import com.praktyki.backend.business.value.Installment;
import com.praktyki.backend.business.value.InsurancePremium;
import com.praktyki.backend.business.value.Schedule;
import com.praktyki.backend.business.value.ScheduleConfiguration;
import com.praktyki.backend.web.models.ScheduleCalculationEventDetailsModel;
import com.praktyki.backend.web.models.ScheduleModel;
import com.praktyki.backend.web.models.converters.ScheduleConverter;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private AmqpTemplate mAmqpTemplate;

    @Autowired
    private ScheduleConverter mScheduleConverter;

    public Schedule calculateSchedule(ScheduleConfiguration configuration) throws NoInsuranceRateForAgeException {
        return createSchedule(configuration);
    }

    public Schedule createSchedule(ScheduleConfiguration scheduleConfiguration) throws NoInsuranceRateForAgeException {

        List<Installment> installments = mInstallmentScheduleService.createInstallmentSchedule(scheduleConfiguration);
        List<InsurancePremium> insurancePremiumList = scheduleConfiguration.isInsurance()
                ? mInsuranceService.calculateInsurancePremium(scheduleConfiguration, installments)
                : Collections.emptyList();
        BigDecimal commission = mInstallmentScheduleService.calculateCommission(scheduleConfiguration);
        BigDecimal insurancePremiumSum = mInsuranceService.calculateTotalInsuranceCost(insurancePremiumList);
        BigDecimal interestInstallmentSum = mInstallmentScheduleService.sumUpInterestInstallment(installments);

        Schedule schedule =  new Schedule(
                scheduleConfiguration,
                installments,
                insurancePremiumList,
                mInstallmentScheduleService.sumUpCapitalInstallment(installments),
                interestInstallmentSum,
                scheduleConfiguration.getCapital().subtract(commission),
                commission,
                insurancePremiumSum,
                commission.add(insurancePremiumSum).add(interestInstallmentSum),
                mAPRCService.calculateAPRC(scheduleConfiguration, installments,
                        insurancePremiumList, commission)
        );

        ScheduleCalculationEventDetailsModel model = new ScheduleCalculationEventDetailsModel(
                0,
                LocalDate.now(),
                mScheduleConverter.convertToModel(schedule)
        );

        mAmqpTemplate.convertAndSend(RabbitMqConfiguration.EXCHANGE_NAME, "", model);

        return schedule;
    }

    public void generatePdf(ScheduleModel scheduleModel, OutputStream outputStream) throws IOException {

        Context context = new Context();
        context.setVariable("schedule", scheduleModel);
        context.setVariable("conf", scheduleModel.scheduleConfiguration);
        context.setVariable("paymentsList", scheduleModel.payments);
        context.setVariable("totalInstallmentSum",scheduleModel.payments.stream()
                .mapToDouble(i -> i.capitalInstallment + i.interestInstallment)
                .sum()
        );
        String url;

        try {
            url = Paths.get("src/main/resources").toUri().toURL().toString();
        } catch (Exception e) {
            throw new IllegalStateException("Invalid resources path", e);
        }

        String html = mITemplateEngine.process("schedule_template", context);
        new PdfRendererBuilder()
                .withHtmlContent(
                        html,
                        url
                )
                .toStream(outputStream)
                .run();
    }


}
