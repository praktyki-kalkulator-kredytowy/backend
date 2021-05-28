package com.praktyki.backend.web.models.converters;

import com.praktyki.backend.business.value.Installment;
import com.praktyki.backend.business.value.InsurancePremium;
import com.praktyki.backend.web.models.PaymentModel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class PaymentConverterImplTest {

    private PaymentConverterImpl mPaymentConverter = new PaymentConverterImpl();

    @Test
    void convertToPayments() {
        List<Installment> installments = Arrays.asList(
                new Installment(
                        1,
                        LocalDate.of(2021,5,28),
                        BigDecimal.valueOf(1),
                        BigDecimal.valueOf(10),
                        BigDecimal.valueOf(100)
                ),
                new Installment(
                        2,
                        LocalDate.of(2021,6,28),
                        BigDecimal.valueOf(2),
                        BigDecimal.valueOf(20),
                        BigDecimal.valueOf(200)
                ),
                new Installment(
                        3,
                        LocalDate.of(2021,7,28),
                        BigDecimal.valueOf(3),
                        BigDecimal.valueOf(30),
                        BigDecimal.valueOf(300)
                ),
                new Installment(
                        4,
                        LocalDate.of(2021,8,28),
                        BigDecimal.valueOf(4),
                        BigDecimal.valueOf(40),
                        BigDecimal.valueOf(400)
                )
        );

        List<PaymentModel> models1 = mPaymentConverter.convertToPayments(installments, Collections.emptyList());
        assertEquals(installments.size(), models1.size());
        models1.forEach(m -> assertNull(m.insurancePremium));

        List<InsurancePremium> premiums = Arrays.asList(
                new InsurancePremium(
                        1,
                        LocalDate.of(2021,5,28),
                        BigDecimal.valueOf(1000)
                ),
                new InsurancePremium(
                        2,
                        LocalDate.of(2021,7,28),
                        BigDecimal.valueOf(3000)
                )
        );


        List<PaymentModel> expectedModels = Arrays.asList(
                new PaymentModel(
                        1,
                        LocalDate.of(2021,5,28),
                        BigDecimal.valueOf(1).doubleValue(),
                        BigDecimal.valueOf(10).doubleValue(),
                        BigDecimal.valueOf(100).doubleValue(),
                        BigDecimal.valueOf(1000).doubleValue()
                ),
                new PaymentModel(
                        2,
                        LocalDate.of(2021,6,28),
                        BigDecimal.valueOf(2).doubleValue(),
                        BigDecimal.valueOf(20).doubleValue(),
                        BigDecimal.valueOf(200).doubleValue(),
                        null
                ),
                new PaymentModel(
                        3,
                        LocalDate.of(2021,7,28),
                        BigDecimal.valueOf(3).doubleValue(),
                        BigDecimal.valueOf(30).doubleValue(),
                        BigDecimal.valueOf(300).doubleValue(),
                        BigDecimal.valueOf(3000).doubleValue()
                ),
                new PaymentModel(
                        4,
                        LocalDate.of(2021,8,28),
                        BigDecimal.valueOf(4).doubleValue(),
                        BigDecimal.valueOf(40).doubleValue(),
                        BigDecimal.valueOf(400).doubleValue(),
                        null
                )
        );


        List<PaymentModel> actualModels = mPaymentConverter.convertToPayments(installments, premiums);

        assertEquals(expectedModels, actualModels);

    }
}