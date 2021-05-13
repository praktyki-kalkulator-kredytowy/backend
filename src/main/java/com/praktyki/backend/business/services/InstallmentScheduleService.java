package com.praktyki.backend.business.services;

import com.praktyki.backend.business.entities.dates.MonthlyDateScheduleCalculator;
import com.praktyki.backend.business.value.Installment;
import com.praktyki.backend.business.value.InsurancePremium;
import com.praktyki.backend.business.value.Schedule;
import com.praktyki.backend.business.value.ScheduleConfiguration;
import com.praktyki.backend.business.entities.dates.DateSchedule;
import com.praktyki.backend.business.entities.dates.DateScheduleCalculator;
import com.praktyki.backend.business.utils.InstallmentUtils;
import com.praktyki.backend.business.utils.MathUtils;
import com.praktyki.backend.configuration.Configuration;
import com.praktyki.backend.configuration.ConfigurationStore;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class InstallmentScheduleService {

    public static final String MIN_COMMISSION_AMOUNT = "MIN_COMMISSION_AMOUNT";
    public static final double MIN_INTEREST_RATE = 0.01;
    public static final String MAX_INTEREST_RATE = "MAX_INTEREST_RATE";
    public static final double MIN_COMMISSION_RATE = 0;
    public static final String  MAX_COMMISSION_RATE = "MAX_COMMISSION_RATE";

    public static double maxCommissionRate;
    public static double maxInterestRate;

    private final MonthlyDateScheduleCalculator mDateScheduleCalculator;
    private Configuration mConfiguration;

    public InstallmentScheduleService(MonthlyDateScheduleCalculator dateScheduleCalculator, ConfigurationStore configurationStore) {
        mDateScheduleCalculator = dateScheduleCalculator;
        mConfiguration = configurationStore.getConfiguration(this.getClass());
        mConfiguration.require(MIN_COMMISSION_AMOUNT, "50", "Minimal amount for commission");
        mConfiguration.require(MAX_COMMISSION_RATE, "0.2", "Max percent for commission rate");
        mConfiguration.require(MAX_INTEREST_RATE,"1", "Max percent for interest rate");
        maxCommissionRate = Double.parseDouble(mConfiguration.get(MAX_COMMISSION_RATE));
        maxInterestRate = Double.parseDouble(mConfiguration.get(MAX_INTEREST_RATE));
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

    public BigDecimal sumUpInterestInstallment(List<Installment> installments) {
        return installments.stream()
                .map(Installment::getInterestInstallment)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateCommission(ScheduleConfiguration scheduleConfiguration) {

        BigDecimal minimalCommissionAmount = new BigDecimal(mConfiguration.get(MIN_COMMISSION_AMOUNT));

        BigDecimal commission = scheduleConfiguration.getCapital().multiply(
                BigDecimal.valueOf(scheduleConfiguration.getCommissionRate()), MathUtils.CONTEXT);

        return commission.compareTo(minimalCommissionAmount) < 0
                ? minimalCommissionAmount.setScale(2, RoundingMode.HALF_UP)
                : commission.setScale(2, RoundingMode.HALF_UP);

    }

}
