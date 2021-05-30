package com.praktyki.backend.web.models.converters;

import com.praktyki.backend.web.models.ScheduleCalculationEventDetailsModel;
import com.praktyki.backend.web.models.ScheduleCalculationEventModel;
import org.springframework.stereotype.Component;

@Component
public class ScheduleCalculationEventConverterImpl implements ScheduleCalculationEventConverter {

    @Override
    public ScheduleCalculationEventModel convertToEvent(ScheduleCalculationEventDetailsModel m) {
        return new ScheduleCalculationEventModel(
                m.id,
                m.schedule.scheduleConfiguration.capital,
                m.schedule.scheduleConfiguration.installmentType,
                m.schedule.scheduleConfiguration.installmentAmount,
                m.schedule.scheduleConfiguration.interestRate,
                m.schedule.scheduleConfiguration.withdrawalDate,
                m.schedule.scheduleConfiguration.commissionRate,
                m.schedule.scheduleConfiguration.age,
                m.schedule.scheduleConfiguration.insurance,
                m.schedule.capitalInstallmentSum,
                m.schedule.interestInstallmentSum,
                m.schedule.loanPaidOutAmount,
                m.schedule.commissionAmount,
                m.schedule.insuranceTotalAmount,
                m.schedule.loanTotalCost,
                m.schedule.aprc,
                m.calculationDate
        );
    }
}
