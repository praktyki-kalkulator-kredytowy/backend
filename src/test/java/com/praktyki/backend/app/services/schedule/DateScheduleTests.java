package com.praktyki.backend.app.services.schedule;

import com.praktyki.backend.business.services.schedule.dates.DateSchedule;
import com.praktyki.backend.business.services.schedule.dates.MonthlyDateCalculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class DateScheduleTests {

    @Test
    @DisplayName("Test of installment schedule dates for a common scenario")
    public void testCommonScenario() {
        List<LocalDate> expectedDates = Arrays.asList(
                LocalDate.of(2021, 6, 5),
                LocalDate.of(2021, 7, 5),
                LocalDate.of(2021, 8, 5),
                LocalDate.of(2021, 9, 5),
                LocalDate.of(2021, 10, 5)
        );

        doTest(expectedDates,LocalDate.of(2021, 5, 5));
    }

    @Test
    @DisplayName("Test of installment schedule dates for a february and year increment")
    public void testFebruary() {
        List<LocalDate> expectedDates = Arrays.asList(
                LocalDate.of(2020, 12, 30),
                LocalDate.of(2021, 1, 30),
                LocalDate.of(2021, 2, 28),
                LocalDate.of(2021, 3, 30),
                LocalDate.of(2021, 4, 30)
        );

        doTest(expectedDates, LocalDate.of(2020, 11, 30));
    }

    @Test
    @DisplayName("Test of installment schedule dates for a 31st day of month")
    public void testForThirtyFirst() {
        List<LocalDate> expectedDates = Arrays.asList(
                LocalDate.of(2021, 2, 28),
                LocalDate.of(2021, 3, 31),
                LocalDate.of(2021, 4, 30),
                LocalDate.of(2021, 5, 31),
                LocalDate.of(2021, 6, 30)
        );

        doTest(expectedDates, LocalDate.of(2021, 1, 31));

    }


    public void doTest(List<LocalDate> expectedDates, LocalDate withdrawalDate) {
        DateSchedule dateSchedule = new MonthlyDateCalculator().calculate(withdrawalDate);

        for(int i = 1; i <= expectedDates.size(); i++) {
            Assertions.assertEquals(expectedDates.get(i-1), dateSchedule.getDateFor(i));
        }
    }

}
