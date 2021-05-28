package com.praktyki.backend.web.models.converters;

import com.praktyki.backend.business.value.Installment;
import com.praktyki.backend.business.value.InsurancePremium;
import com.praktyki.backend.web.models.PaymentModel;

import java.util.List;

public interface PaymentConverter {

     List<PaymentModel> convertToPayments(List<Installment> installments, List<InsurancePremium> insurancePremiums);

}
