package com.praktyki.backend.web.models.converters;

import com.praktyki.backend.business.value.Installment;
import com.praktyki.backend.business.value.InsurancePremium;
import com.praktyki.backend.web.models.PaymentModel;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
public class PaymentConverterImpl implements PaymentConverter {

    @Override
    public List<PaymentModel> convertToPayments(List<Installment> installments, List<InsurancePremium> insurancePremiums) {
        List<PaymentModel> payments = new LinkedList<>();

        for(Installment i : installments) {
            Optional<BigDecimal> premiumAmount = insurancePremiums.stream()
                    .filter(p -> p.getDate().equals(i.getDate()))
                    .findFirst()
                    .map(InsurancePremium::getAmount);

            payments.add(new PaymentModel(
                    i.getIndex(),
                    i.getDate(),
                    i.getCapitalInstallment().doubleValue(),
                    i.getInterestInstallment().doubleValue(),
                    i.getRemainingDebt().doubleValue(),
                    premiumAmount.map(BigDecimal::doubleValue).orElse(null)
            ));
        }
        return payments;
    }
}
