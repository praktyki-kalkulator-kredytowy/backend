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
        BigDecimal sumUpInsurancePremium = mInsuranceService.calculateTotalInsuranceCost(insurancePremiumList);
        BigDecimal sumUpInterestInstallment = mInstallmentScheduleService.sumUpInterestInstallment(installments);

        Schedule schedule =  new Schedule(
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

        ScheduleCalculationEventDetailsModel model = new ScheduleCalculationEventDetailsModel(
                0,
                LocalDate.now(),
                mScheduleConverter.convertToModel(schedule)
        );

        mAmqpTemplate.convertAndSend(RabbitMqConfiguration.EXCHANGE_NAME, "", model);

        return schedule;
    }

    public void generatePdf(Schedule schedule, OutputStream outputStream) throws IOException {
        Context context = new Context();
        context.setVariable("schedule", schedule);
        context.setVariable("conf", schedule.getScheduleConfiguration());
        context.setVariable("paymentsList", createPaymentTable(schedule));
        context.setVariable("interestInstallmentSum", schedule.getInstallmentList().stream()
                .map(Installment::getInterestInstallment)
                .reduce(BigDecimal::add)
                .orElse(null)
        );
        context.setVariable("totalInstallmentSum",schedule.getInstallmentList().stream()
                .map(i -> i.getCapitalInstallment().add(i.getInterestInstallment()))
                .reduce(BigDecimal::add)
                .orElse(null)
        );
        context.setVariable("confDate", Date.valueOf(schedule.getScheduleConfiguration().getWithdrawalDate()));
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


    // TODO: Use models instead
    private List<ContextPayment> createPaymentTable(Schedule schedule) {
        List<ContextPayment> contextPayments = new LinkedList<>();

        for(Installment installment : schedule.getInstallmentList()) {
            Optional<InsurancePremium> insurancePremium = schedule.getInsurancePremiumList().stream()
                    .filter(p -> p.getDate().equals(installment.getDate()))
                    .findFirst();

            contextPayments.add(new ContextPayment(
                    Date.valueOf(installment.getDate()),
                    installment,
                    insurancePremium.isPresent(),
                    insurancePremium.orElse(null)
            ));
        }
        return contextPayments;
    }


    private class ContextPayment {
        public Date paymentDate;
        public Installment installment;
        public boolean hasInsurance;
        public InsurancePremium insurancePremium;

        public ContextPayment(
                Date paymentDate, Installment installment,
                boolean hasInsurance, InsurancePremium insurancePremium)
        {
            this.paymentDate = paymentDate;
            this.installment = installment;
            this.hasInsurance = hasInsurance;
            this.insurancePremium = insurancePremium;
        }
    }

}
