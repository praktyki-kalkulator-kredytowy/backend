package com.praktyki.backend.business.services;

import com.praktyki.backend.business.utils.MathUtils;
import com.praktyki.backend.business.value.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.function.UnaryOperator;

public class APRCService {

    public static final BigDecimal RANGE_START = BigDecimal.ZERO;
    public static final BigDecimal RANGE_END = BigDecimal.valueOf(1000);
    public static final BigDecimal PRECISION = BigDecimal.valueOf(0.0000001);

    public BigDecimal calculateAPRC(
            ScheduleConfiguration conf,
            List<Installment> installments,
            List<InsurancePremium> premiums,
            BigDecimal commission
            ) {

        List<Payment> payments = new LinkedList<>();
        payments.addAll(installments);
        payments.addAll(premiums);
        payments.add(new PaymentImpl(
                conf.getWithdrawalDate(),
                commission
        ));


        UnaryOperator<BigDecimal> APRCFunction = createAPRCFunction(
                conf.getCapital(),
                payments,
                conf.getWithdrawalDate()
        );

        return MathUtils.solveForZeroWithBisection(
                APRCFunction,
                RANGE_START,
                RANGE_END,
                PRECISION
        );
    }

    private UnaryOperator<BigDecimal> createAPRCFunction(
            BigDecimal capital,
            List<Payment> payments,
            LocalDate withdrawalDate) { return x -> {

        BigDecimal sum = BigDecimal.ZERO;

        for(Payment p : payments) {
            double yearProgress =
                    BigDecimal.valueOf(ChronoUnit.DAYS.between(withdrawalDate, p.getDate()))
                    .divide(BigDecimal.valueOf(p.getDate().lengthOfYear()), MathUtils.CONTEXT)
                    .doubleValue();

            sum = sum.add(
                    p.getAmount().divide(
                            BigDecimal.valueOf(Math.pow(1 + x.doubleValue(), yearProgress)),
                            MathUtils.CONTEXT
                    )
            );
        }

        return capital.subtract(sum);
    };}





}
