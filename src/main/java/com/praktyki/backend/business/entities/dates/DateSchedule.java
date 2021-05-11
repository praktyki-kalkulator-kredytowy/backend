package com.praktyki.backend.business.entities.dates;


import java.time.LocalDate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface DateSchedule extends Iterable<DateSchedule.Entry> {
    LocalDate getDateFor(int installmentIndex);

    DateSchedule shift(int i);

    default Stream<Entry> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    interface Entry {
        int getIndex();
        LocalDate getDate();
    }

}
