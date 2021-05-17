package com.praktyki.backend.business.utils;

import java.math.MathContext;
import java.math.RoundingMode;
import java.util.function.UnaryOperator;

public class MathUtils {

    public static final MathContext CONTEXT = new MathContext(32, RoundingMode.HALF_UP);


    public static double solveForZeroWithBisection(
            UnaryOperator<Double> f,
            double start,
            double end,
            double precision) {

        double startValue = f.apply(start);
        double endValue = f.apply(end);

        while(Math.abs(start - end) > precision) {
            double x = (start + end) / 2;
            double xValue = f.apply(x);

            if(xValue == 0) return x;

            if(startValue * xValue < 0)
                end = x;
            else
                start = x;
        }
        return (start + end) / 2;
    }

}
