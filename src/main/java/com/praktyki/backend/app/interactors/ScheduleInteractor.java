package com.praktyki.backend.app.interactors;

import com.praktyki.backend.business.services.APRCService;
import com.praktyki.backend.business.services.InsuranceService;
import com.praktyki.backend.business.value.Installment;
import com.praktyki.backend.business.value.InsurancePremium;
import com.praktyki.backend.business.value.Schedule;
import com.praktyki.backend.business.value.ScheduleConfiguration;
import com.praktyki.backend.business.services.InstallmentScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ScheduleInteractor {

    @Autowired
    private InstallmentScheduleService mInstallmentScheduleService;

    @Autowired
    private InsuranceService mInsuranceService;

    @Autowired
    private APRCService mAPRCService;

    public Schedule calculateSchedule(ScheduleConfiguration configuration) {
        return createSchedule(configuration);
    }

    public Schedule createSchedule(ScheduleConfiguration scheduleConfiguration) {

        List<Installment> installments = mInstallmentScheduleService.createInstallmentSchedule(scheduleConfiguration);
        List<InsurancePremium> insurancePremiumList = mInsuranceService.calculateInsurancePremium(scheduleConfiguration, installments);
        BigDecimal commission = mInstallmentScheduleService.calculateCommission(scheduleConfiguration);
        BigDecimal sumUpInsurancePremium = mInsuranceService.calculateTotalInsuranceCost(scheduleConfiguration);
        BigDecimal sumUpInterestInstallment = mInstallmentScheduleService.sumUpInterestInstallment(installments);

        return new Schedule(
                scheduleConfiguration,
                installments,
                insurancePremiumList,
                scheduleConfiguration.getCapital().subtract(commission),
                commission,
                sumUpInsurancePremium,
                commission.add(sumUpInsurancePremium).add(sumUpInterestInstallment),
                mAPRCService.calculateAPRC(scheduleConfiguration, installments,
                        insurancePremiumList, commission)
        );

    }

}
