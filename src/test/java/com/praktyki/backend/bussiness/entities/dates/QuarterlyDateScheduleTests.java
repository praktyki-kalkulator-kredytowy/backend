package com.praktyki.backend.bussiness.entities.dates;

import com.praktyki.backend.business.entities.dates.DateSchedule;
import com.praktyki.backend.business.entities.dates.QuarterlyDateScheduleCalculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class QuarterlyDateScheduleTests {

    private final List<LocalDate> expected = Arrays.asList(
            LocalDate.of(2021, 5, 31),
            LocalDate.of(2021, 8, 31),
            LocalDate.of(2021, 11, 30),
            LocalDate.of(2022, 2, 28),
            LocalDate.of(2022, 5, 31),
            LocalDate.of(2022, 8, 31)

    );

    @Test
    public void testQuarterlyDateSchedule() {


        DateSchedule schedule = new QuarterlyDateScheduleCalculator().calculate(LocalDate.of(2021, 5, 31));

        for(int i = 1; i <= expected.size(); i++)
            Assertions.assertEquals(expected.get(i - 1), schedule.getDateFor(i));




    }

    @Test
    public void testQuarterlyDateScheduleWithShift() {
        DateSchedule schedule = new QuarterlyDateScheduleCalculator().calculate(LocalDate.of(2021, 5, 31)).shift(1);

        for(int i = 1; i <= expected.size() - 1; i++)
            Assertions.assertEquals(expected.get(i), schedule.getDateFor(i));
    }
}
