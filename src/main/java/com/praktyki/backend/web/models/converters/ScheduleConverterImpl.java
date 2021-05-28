package com.praktyki.backend.web.models.converters;

import com.praktyki.backend.business.value.Schedule;
import com.praktyki.backend.web.models.ScheduleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ScheduleConverterImpl implements ScheduleConverter {

    @Autowired
    private ScheduleConfigurationConverter mScheduleConfigurationConverter;

    @Autowired
    private PaymentConverter mPaymentConverter;

    @Override
    public ScheduleModel convertToModel(Schedule schedule) {


        return new ScheduleModel(
                mScheduleConfigurationConverter.convertToScheduleConfigurationModel(schedule.getScheduleConfiguration()),
                mPaymentConverter.convertToPayments(schedule.getInstallmentList(), schedule.getInsurancePremiumList()),
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
