package com.praktyki.backend.business.services;

import com.praktyki.backend.business.value.InsurancePremium;
import com.praktyki.backend.business.value.ScheduleConfiguration;

import java.math.BigDecimal;
import java.util.List;

public class InsuranceService {

    public List<InsurancePremium> calculateInsurancePremium(ScheduleConfiguration scheduleConfiguration) {

        return null;

    }

    public BigDecimal sumUpInsurancePremium(List<InsurancePremium> insurancePremium) {
        return insurancePremium.stream()
                .map(InsurancePremium::getInsurancePremiumValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
