package com.praktyki.backend.business.services;

import com.praktyki.backend.app.configuration.ConfigurationKeys;
import com.praktyki.backend.business.entities.dates.CustomDateScheduleCalculator;
import com.praktyki.backend.business.entities.dates.DateSchedule;
import com.praktyki.backend.business.entities.dates.QuarterlyDateScheduleCalculator;
import com.praktyki.backend.business.utils.MathUtils;
import com.praktyki.backend.business.value.Installment;
import com.praktyki.backend.business.value.InsurancePremium;
import com.praktyki.backend.business.value.ScheduleConfiguration;
import com.praktyki.backend.configuration.Configuration;
import com.praktyki.backend.configuration.ConfigurationGroup;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class InsuranceService {

    private CustomDateScheduleCalculator mDateScheduleCalculator;

    private Configuration mConfiguration;

    public InsuranceService(CustomDateScheduleCalculator dateScheduleCalculator, Configuration configuration) {
        mDateScheduleCalculator = dateScheduleCalculator;
        mConfiguration = configuration;
        mDateScheduleCalculator.setMonthFrame(Long.parseLong(mConfiguration.get(ConfigurationKeys.MONTH_FRAME)));
    }

    public List<InsurancePremium> calculateInsurancePremium(
            ScheduleConfiguration scheduleConfiguration,
            List<Installment> installments) {

        BigDecimal minPremiumValue = new BigDecimal(mConfiguration.get(ConfigurationKeys.MIN_PREMIUM_VALUE));

        DateSchedule schedule = mDateScheduleCalculator.calculate(installments.get(0).getInstallmentDate());


        int premiumAmount = ((int) ChronoUnit.MONTHS.between(
                installments.get(0).getInstallmentDate(),
                installments.get(installments.size() - 1).getInstallmentDate()
        ) + 1) / 3;


        BigDecimal totalInsurance = scheduleConfiguration
                .getCapital()
                .multiply(BigDecimal.valueOf(scheduleConfiguration.getInsuranceRate()), MathUtils.CONTEXT);

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

    public BigDecimal calculateTotalInsuranceCost(ScheduleConfiguration conf) {
        return conf.getCapital()
                .multiply(BigDecimal.valueOf(conf.getInsuranceRate()), MathUtils.CONTEXT)
                .setScale(2, RoundingMode.HALF_UP);
    }

}
