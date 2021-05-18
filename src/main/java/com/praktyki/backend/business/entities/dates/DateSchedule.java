package com.praktyki.backend.business.entities.dates;


import java.time.LocalDate;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface DateSchedule extends Iterable<DateSchedule.Entry> {
    LocalDate getDateFor(int installmentIndex);

    DateSchedule shift(int i);

    DateSchedule shift(TemporalAmount amount);

    default Stream<Entry> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    interface Entry {
        int getIndex();
        LocalDate getDate();
    }

}
