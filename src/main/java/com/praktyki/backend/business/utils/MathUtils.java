package com.praktyki.backend.business.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.function.UnaryOperator;

public class MathUtils {

    public static final MathContext CONTEXT = new MathContext(32, RoundingMode.HALF_UP);

    public static BigDecimal solveForZeroWithBisection(
            UnaryOperator<BigDecimal> f,
            BigDecimal start,
            BigDecimal end,
            BigDecimal precision) {

        BigDecimal startValue = f.apply(start);
        BigDecimal endValue = f.apply(end);

        while(start.subtract(end).abs().compareTo(precision) >= 0) {
            BigDecimal x = start.add(end).divide(BigDecimal.valueOf(2), CONTEXT);
            BigDecimal xValue = f.apply(x);

            if(xValue.equals(BigDecimal.ZERO)) return x;

            if(startValue.signum() * xValue.signum() < 0)
                end = x;
            else
                start = x;
        }
        return start.add(end).divide(BigDecimal.valueOf(2), CONTEXT);
    }

    public static boolean isEqual(double d, BigDecimal bd) {
        return BigDecimal.valueOf(d).compareTo(bd) == 0;
    }

}
