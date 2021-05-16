package com.praktyki.backend.business.services;

import com.praktyki.backend.app.configuration.ConfigurationKeys;
import com.praktyki.backend.business.entities.dates.DateSchedule;
import com.praktyki.backend.business.entities.dates.MonthlyDateScheduleCalculator;
import com.praktyki.backend.business.utils.InstallmentUtils;
import com.praktyki.backend.business.utils.MathUtils;
import com.praktyki.backend.business.value.Installment;
import com.praktyki.backend.business.value.ScheduleConfiguration;
import com.praktyki.backend.configuration.Configuration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class InstallmentScheduleService {

    private final MonthlyDateScheduleCalculator mDateScheduleCalculator;
    private Configuration mConfiguration;

    public InstallmentScheduleService(MonthlyDateScheduleCalculator dateScheduleCalculator, Configuration configuration) {
        mDateScheduleCalculator = dateScheduleCalculator;
        mConfiguration = configuration;
    }

    public List<Installment> createInstallmentSchedule(ScheduleConfiguration scheduleConfiguration) {

        DateSchedule dateSchedule = mDateScheduleCalculator.calculate(scheduleConfiguration.getWithdrawalDate()).shift(1);

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

    public BigDecimal sumUpCapitalInstallment(List<Installment> installments) {
        return installments.stream()
                .map(Installment::getCapitalInstallment)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal sumUpInterestInstallment(List<Installment> installments) {
        return installments.stream()
                .map(Installment::getInterestInstallment)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateCommission(ScheduleConfiguration scheduleConfiguration) {

        BigDecimal minimalCommissionAmount = new BigDecimal(mConfiguration.get(ConfigurationKeys.MIN_COMMISSION_AMOUNT));

        BigDecimal commission = scheduleConfiguration.getCapital().multiply(
                BigDecimal.valueOf(scheduleConfiguration.getCommissionRate()), MathUtils.CONTEXT);

        return commission.compareTo(minimalCommissionAmount) > 0 || commission.equals(BigDecimal.ZERO)
                ? commission.setScale(2, RoundingMode.HALF_UP)
                : minimalCommissionAmount.setScale(2, RoundingMode.HALF_UP);

    }

}
