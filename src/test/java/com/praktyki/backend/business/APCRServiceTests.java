package com.praktyki.backend.business;

import com.praktyki.backend.business.entities.InstallmentType;
import com.praktyki.backend.business.entities.dates.MonthlyDateScheduleCalculator;
import com.praktyki.backend.business.entities.dates.QuarterlyDateScheduleCalculator;
import com.praktyki.backend.business.services.APRCService;
import com.praktyki.backend.business.services.InstallmentScheduleService;
import com.praktyki.backend.business.services.InsuranceService;
import com.praktyki.backend.business.value.Installment;
import com.praktyki.backend.business.value.InsurancePremium;
import com.praktyki.backend.business.value.ScheduleConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest( classes = {
        InstallmentScheduleService.class,
        InsuranceService.class,
        MonthlyDateScheduleCalculator.class,
        QuarterlyDateScheduleCalculator.class,
        APRCService.class,
})
public class APCRServiceTests {

    @Autowired
    private InstallmentScheduleService mInstallmentScheduleService;

    @Autowired
    private InsuranceService mInsuranceService;

    @Autowired
    private APRCService mAPRCService;

    @Test
    public void test() {

        ScheduleConfiguration conf = ScheduleConfiguration.builder()
                .setInstallmentType(InstallmentType.CONSTANT)
                .setInterestRate(0.1)
                .setCapital(BigDecimal.valueOf(10000))
                .setInstallmentAmount(12)
                .setCommissionRate(0.05)
                .setInsuranceRate(0.1)
                .setWithdrawalDate(LocalDate.of(2021, 4, 11))
                .build();

        List<Installment> installments = mInstallmentScheduleService.createInstallmentSchedule(conf);
        List<InsurancePremium> insurancePremiums = mInsuranceService.calculateInsurancePremium(conf, installments);

        // TODO: Assert
        System.out.println(mAPRCService.calculateAPRC(
                conf,
                installments,
                insurancePremiums,
                mInstallmentScheduleService.calculateCommission(conf)
        ));

    }
}
