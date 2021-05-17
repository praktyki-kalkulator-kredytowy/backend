package com.praktyki.backend.business.services;

import com.praktyki.backend.app.configuration.ConfigurationKeys;
import com.praktyki.backend.business.entities.InsuranceRateConfiguration;
import com.praktyki.backend.business.entities.dates.DateSchedule;
import com.praktyki.backend.business.entities.dates.InsurancePremiumDateCalculator;
import com.praktyki.backend.business.services.exceptions.NoInsuranceRateForAgeException;
import com.praktyki.backend.business.utils.MathUtils;
import com.praktyki.backend.business.value.Installment;
import com.praktyki.backend.business.value.InsurancePremium;
import com.praktyki.backend.business.value.ScheduleConfiguration;
import com.praktyki.backend.configuration.Configuration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class InsuranceService {

    private InsurancePremiumDateCalculator mDateScheduleCalculator;

    private Configuration mConfiguration;

    private InsuranceRateConfiguration mInsuranceRateConfiguration;

    public InsuranceService(
            InsurancePremiumDateCalculator dateScheduleCalculator,
            Configuration configuration,
            InsuranceRateConfiguration installmentDateConfiguration) {

        mDateScheduleCalculator = dateScheduleCalculator;
        mConfiguration = configuration;
        mInsuranceRateConfiguration = installmentDateConfiguration;
    }

    public List<InsurancePremium> calculateInsurancePremium(
            ScheduleConfiguration scheduleConfiguration,
            List<Installment> installments) throws NoInsuranceRateForAgeException {

        BigDecimal minPremiumValue = new BigDecimal(mConfiguration.get(ConfigurationKeys.MIN_PREMIUM_VALUE))
                .setScale(2, RoundingMode.HALF_UP);

        DateSchedule schedule = mDateScheduleCalculator.calculate(installments.get(0).getInstallmentDate());

        BigDecimal insuranceRate = BigDecimal.valueOf(
                mInsuranceRateConfiguration.getRateForAge(scheduleConfiguration.getAge())
        );


        int premiumAmount = ((int) ChronoUnit.MONTHS.between(
                installments.get(0).getInstallmentDate(),
                installments.get(installments.size() - 1).getInstallmentDate()
        ) + 1) / Integer.parseInt(mConfiguration.get(ConfigurationKeys.MONTH_FRAME));

        if(premiumAmount == 0) premiumAmount = 1;


        BigDecimal totalInsurance = scheduleConfiguration
                .getCapital()
                .multiply(insuranceRate, MathUtils.CONTEXT);

        BigDecimal dividedPremiumValue = totalInsurance.divide(
                BigDecimal.valueOf(premiumAmount), 2, RoundingMode.HALF_UP
        );

        BigDecimal premiumValue = dividedPremiumValue.equals(new BigDecimal("0.00"))
                || dividedPremiumValue.compareTo(minPremiumValue) > 0
                ? dividedPremiumValue : minPremiumValue;

        List<InsurancePremium> premiums = schedule.stream()
                .limit(premiumAmount - 1)
                .map(d -> new InsurancePremium(d.getIndex(), d.getDate(), premiumValue))
                .collect(Collectors.toList());

        BigDecimal lastPremiumValue = totalInsurance.subtract(premiums.stream()
                .map(InsurancePremium::getInsurancePremiumValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        premiums.add(new InsurancePremium(
                premiums.size() + 1,
                schedule.getDateFor(premiums.size() + 1),
                lastPremiumValue.equals(new BigDecimal("0.00")) || lastPremiumValue.compareTo(minPremiumValue) > 0
                ? lastPremiumValue : minPremiumValue
        ));

        return premiums;
    }

    public BigDecimal calculateTotalInsuranceCost(List<InsurancePremium> insurancePremiumList) {
        return insurancePremiumList.stream()
                .map(InsurancePremium::getInsurancePremiumValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


}
