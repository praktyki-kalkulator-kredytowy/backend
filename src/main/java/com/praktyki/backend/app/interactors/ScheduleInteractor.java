package com.praktyki.backend.app.interactors;

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

import java.math.BigDecimal;
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

}
