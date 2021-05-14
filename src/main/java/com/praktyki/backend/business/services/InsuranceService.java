package com.praktyki.backend.business.services;

import com.praktyki.backend.app.configuration.ConfigurationGroupKeys;
import com.praktyki.backend.app.configuration.ConfigurationKeys;
import com.praktyki.backend.business.entities.InstallmentRateConfiguration;
import com.praktyki.backend.business.entities.dates.CustomDateScheduleCalculator;
import com.praktyki.backend.business.entities.dates.DateSchedule;
import com.praktyki.backend.business.services.exceptions.NoInsuranceRateForAgeException;
import com.praktyki.backend.business.utils.MathUtils;
import com.praktyki.backend.business.value.Installment;
import com.praktyki.backend.business.value.InsurancePremium;
import com.praktyki.backend.business.value.ScheduleConfiguration;
import com.praktyki.backend.configuration.Configuration;
import com.praktyki.backend.configuration.ConfigurationEntry;
import com.praktyki.backend.configuration.ConfigurationKey;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class InsuranceService {

    private CustomDateScheduleCalculator mDateScheduleCalculator;

    private Configuration mConfiguration;

    private InstallmentRateConfiguration mInstallmentRateConfiguration;

    public InsuranceService(
            CustomDateScheduleCalculator dateScheduleCalculator,
            Configuration configuration,
            InstallmentRateConfiguration installmentDateConfiguration) {

        mDateScheduleCalculator = dateScheduleCalculator;
        mConfiguration = configuration;
        mDateScheduleCalculator.setMonthFrame(Long.parseLong(mConfiguration.get(ConfigurationKeys.MONTH_FRAME)));
        mInstallmentRateConfiguration = installmentDateConfiguration;
    }

    public List<InsurancePremium> calculateInsurancePremium(
            ScheduleConfiguration scheduleConfiguration,
            List<Installment> installments) throws NoInsuranceRateForAgeException {

        BigDecimal minPremiumValue = new BigDecimal(mConfiguration.get(ConfigurationKeys.MIN_PREMIUM_VALUE));

        DateSchedule schedule = mDateScheduleCalculator.calculate(installments.get(0).getInstallmentDate());

        BigDecimal insuranceRate = BigDecimal.valueOf(
                mInstallmentRateConfiguration.getRateForAge(scheduleConfiguration.getAge())
        );


        int premiumAmount = ((int) ChronoUnit.MONTHS.between(
                installments.get(0).getInstallmentDate(),
                installments.get(installments.size() - 1).getInstallmentDate()
        ) + 1) / Integer.parseInt(mConfiguration.get(ConfigurationKeys.MONTH_FRAME));


        BigDecimal totalInsurance = scheduleConfiguration
                .getCapital()
                .multiply(insuranceRate, MathUtils.CONTEXT);

        BigDecimal premiumValue = minPremiumValue.max(
                totalInsurance.divide(BigDecimal.valueOf(premiumAmount), 2, RoundingMode.HALF_UP)
        );

        List<InsurancePremium> premiums = schedule.stream()
                .limit(premiumAmount - 1)
                .map(d -> new InsurancePremium(d.getIndex(), d.getDate(), premiumValue))
                .collect(Collectors.toList());

        premiums.add(new InsurancePremium(
                premiums.size() + 1,
                schedule.getDateFor(premiums.size() + 1),
                minPremiumValue.max(totalInsurance.subtract(premiums.stream()
                        .map(InsurancePremium::getInsurancePremiumValue)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)))
        ));

        return premiums;
    }

    public BigDecimal calculateTotalInsuranceCost(ScheduleConfiguration conf) throws NoInsuranceRateForAgeException {
        return conf.getCapital()
                .multiply(
                        BigDecimal.valueOf(mInstallmentRateConfiguration.getRateForAge(conf.getAge())), MathUtils.CONTEXT
                )
                .setScale(2, RoundingMode.HALF_UP);
    }


}
