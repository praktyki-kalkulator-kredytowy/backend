package com.praktyki.backend.business.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.UnaryOperator;

public class MathUtilsTest {

    @Test
    public void testSolveForZeroWithBisection() {

        Assertions.assertEquals(BigDecimal.valueOf(-2.5), BigDecimal.valueOf(MathUtils.solveForZeroWithBisection(
                createFunction(5, 2),
                -50,
                30,
                0.001
        )).setScale(1, RoundingMode.HALF_UP));

        Assertions.assertEquals(BigDecimal.valueOf(-0.446), BigDecimal.valueOf(MathUtils.solveForZeroWithBisection(
                createFunction(-7, -13, 6),
                -10,
                1,
                0.0001
        )).setScale(3, RoundingMode.HALF_UP));

        Assertions.assertEquals(BigDecimal.valueOf(-2.855), BigDecimal.valueOf(MathUtils.solveForZeroWithBisection(
                createFunction(123, -7, 31, 17),
                -20,
                100,
                0.0001
        )).setScale(3, RoundingMode.HALF_UP));

    }

    public UnaryOperator<Double> createFunction(double... factors) {
        return x -> {
            double result = 0;
            for(int i = 0; i < factors.length; i++)
                result += (Math.pow(x, i) * factors[i]);
            return result;
        };
    }

}
