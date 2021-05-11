package com.praktyki.backend.business.services.schedule;

import com.praktyki.backend.business.value.Installment;
import com.praktyki.backend.business.value.InsurancePremium;
import com.praktyki.backend.business.value.Schedule;
import com.praktyki.backend.business.value.ScheduleConfiguration;
import com.praktyki.backend.business.services.schedule.dates.DateSchedule;
import com.praktyki.backend.business.services.schedule.dates.DateScheduleCalculator;
import com.praktyki.backend.business.utils.InstallmentUtils;
import com.praktyki.backend.business.utils.MathUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ScheduleService {

    private final DateScheduleCalculator mDateScheduleCalculator;

    public ScheduleService(DateScheduleCalculator dateScheduleCalculator) {
        mDateScheduleCalculator = dateScheduleCalculator;
    }

    public Schedule createSchedule(ScheduleConfiguration scheduleConfiguration) {

        List<Installment> installments = createInstallmentSchedule(scheduleConfiguration);
        List<InsurancePremium> insurancePremiumList = calculateInsurancePremium(scheduleConfiguration);
        BigDecimal commission = calculateCommission(scheduleConfiguration);
        BigDecimal sumUpInsurancePremium = sumUpInsurancePremium(insurancePremiumList);
        BigDecimal sumUpInterestInstallment = sumUpInterestInstallment(installments);

        return new Schedule(
                scheduleConfiguration,
                installments,
                insurancePremiumList,
                scheduleConfiguration.getCapital().subtract(commission),
                commission,
                sumUpInsurancePremium,
                commission.add(sumUpInsurancePremium).add(sumUpInterestInstallment)
        );

    }

    public List<Installment> createInstallmentSchedule(ScheduleConfiguration scheduleConfiguration) {

        DateSchedule dateSchedule = mDateScheduleCalculator.calculate(scheduleConfiguration.getWithdrawalDate());

        List<Installment> installmentList = new LinkedList<>();

        BigDecimal capitalInstallmentSum = BigDecimal.ZERO;

        for(int i = 0; i < scheduleConfiguration.getInstallmentAmount() - 1; i++ ) {

            installmentList.add(scheduleConfiguration.getInstallmentType().calculate(
                    installmentList.isEmpty() ? null : installmentList.get(i - 1),
                    scheduleConfiguration,
                    dateSchedule.getDateFor(i + 1)
            ));

            capitalInstallmentSum = capitalInstallmentSum.add(
                    installmentList.get(i).getCapitalInstallment(), MathUtils.CONTEXT);
        }

        BigDecimal interestInstallment = InstallmentUtils.calculateInterestInstallment(
                scheduleConfiguration,
                installmentList.get(installmentList.size()-1),
                dateSchedule.getDateFor(scheduleConfiguration.getInstallmentAmount())
        );


        installmentList.add(new Installment(
                        scheduleConfiguration.getInstallmentAmount(),
                        dateSchedule.getDateFor(scheduleConfiguration.getInstallmentAmount()),
                        scheduleConfiguration.getCapital().subtract(capitalInstallmentSum, MathUtils.CONTEXT),
                        interestInstallment,
                        BigDecimal.ZERO
                        ));

        return installmentList.stream().peek(Installment::round).collect(Collectors.toList());

    }

    public BigDecimal sumUpInterestInstallment(List<Installment> installments) {
        return installments.stream()
                .map(Installment::getInterestInstallment)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateCommission(ScheduleConfiguration scheduleConfiguration) {

        BigDecimal commission = scheduleConfiguration.getCapital().multiply(
                BigDecimal.valueOf(scheduleConfiguration.getCommissionRate()), MathUtils.CONTEXT);

        return commission.compareTo(new BigDecimal("50")) < 0
                ? new BigDecimal("50").setScale(2, RoundingMode.HALF_UP)
                : commission.setScale(2, RoundingMode.HALF_UP);

    }

    public List<InsurancePremium> calculateInsurancePremium(ScheduleConfiguration scheduleConfiguration) {

        return null;

    }

    public BigDecimal sumUpInsurancePremium(List<InsurancePremium> insurancePremium) {
        return insurancePremium.stream()
                .map(InsurancePremium::getInsurancePremiumValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
