package com.praktyki.backend.business.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.UnaryOperator;

public class MathUtilsTest {

    @Test
    public void testSolveForZeroWithBisection() {

        Assertions.assertEquals(BigDecimal.valueOf(-2.5), MathUtils.solveForZeroWithBisection(
                createFunction(5, 2),
                BigDecimal.valueOf(-50),
                BigDecimal.valueOf(30),
                BigDecimal.valueOf(0.001)
        ).setScale(1, RoundingMode.HALF_UP));

        Assertions.assertEquals(BigDecimal.valueOf(-0.446), MathUtils.solveForZeroWithBisection(
                createFunction(-7, -13, 6),
                BigDecimal.valueOf(-10),
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(0.0001)
        ).setScale(3, RoundingMode.HALF_UP));

        Assertions.assertEquals(BigDecimal.valueOf(-2.855), MathUtils.solveForZeroWithBisection(
                createFunction(123, -7, 31, 17),
                BigDecimal.valueOf(-20),
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(0.0001)
        ).setScale(3, RoundingMode.HALF_UP));

    }

    public UnaryOperator<BigDecimal> createFunction(double... factors) {
        return x -> {
            BigDecimal result = BigDecimal.ZERO;
            for(int i = 0; i < factors.length; i++)
                result = result.add(x.pow(i).multiply(BigDecimal.valueOf(factors[i]), MathUtils.CONTEXT));
            return result;
        };
    }

}
