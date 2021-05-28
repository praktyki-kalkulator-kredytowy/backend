package com.praktyki.backend.web.models.converters;

import com.praktyki.backend.business.value.Installment;
import com.praktyki.backend.business.value.InsurancePremium;
import com.praktyki.backend.business.value.Schedule;
import com.praktyki.backend.web.models.PaymentModel;
import com.praktyki.backend.web.models.ScheduleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
public class ScheduleConverterImpl implements ScheduleConverter {

    @Autowired
    private ScheduleConfigurationConverter mScheduleConfigurationConverter;

    @Override
    public ScheduleModel convertToModel(Schedule schedule) {
        List<PaymentModel> payments = new LinkedList<>();

        for(Installment i : schedule.getInstallmentList()) {
            Optional<BigDecimal> premiumAmount = schedule
                    .getInsurancePremiumList()
                    .stream()
                    .filter(p -> p.getDate().equals(i.getDate()))
                    .findFirst()
                    .map(InsurancePremium::getAmount);

            payments.add(new PaymentModel(
                    i.getIndex(),
                    i.getDate(),
                    i.getCapitalInstallment().doubleValue(),
                    i.getInterestInstallment().doubleValue(),
                    i.getRemainingDebt().doubleValue(),
                    premiumAmount.map(BigDecimal::doubleValue).orElse(null)
            ));
        }

        return new ScheduleModel(
                mScheduleConfigurationConverter.convertToScheduleConfigurationModel(schedule.getScheduleConfiguration()),
                payments,
                schedule.getCapitalInstallmentSum().doubleValue(),
                schedule.getInterestInstallmentSum().doubleValue(),
                schedule.getLoanPaidOutAmount().doubleValue(),
                schedule.getCommissionAmount().doubleValue(),
                schedule.getInsuranceTotalAmount().doubleValue(),
                schedule.getLoanTotalCost().doubleValue(),
                schedule.getAPRC().doubleValue()

        );

    }
}
