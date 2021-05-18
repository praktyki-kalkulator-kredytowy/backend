package com.praktyki.backend.business.entities.dates;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.Iterator;
import java.util.Objects;

abstract class BaseDateSchedule implements DateSchedule {

    private final LocalDate mStartDate;

    private Period shift = Period.ZERO;

    protected LocalDate getStartDate() {
        return mStartDate;
    }

    public BaseDateSchedule(LocalDate startDate) {
        this.mStartDate = startDate;
    }

    abstract TemporalAmount getAmountToAdd(int index);

    @Override
    public LocalDate getDateFor(int installmentIndex) {
        return mStartDate.plus(shift.plus(getAmountToAdd(installmentIndex)));
    }

    @Override
    public Iterator<Entry> iterator() {
        return new ScheduleIterator(this);
    }

    @Override
    public DateSchedule shift(int i) {
        BaseDateSchedule outer = this;
        return new BaseDateSchedule(mStartDate) {
            @Override
            TemporalAmount getAmountToAdd(int index) {
                return outer.getAmountToAdd(index);
            }

            @Override
            public LocalDate getDateFor(int installmentIndex) {
                return outer.getDateFor(installmentIndex + i);
            }
        };
    }

    @Override
    public DateSchedule shift(TemporalAmount amount) {
        BaseDateSchedule outer = this;
        BaseDateSchedule schedule =  new BaseDateSchedule(mStartDate) {
            @Override
            TemporalAmount getAmountToAdd(int index) {
                return outer.getAmountToAdd(index);
            }
        };
        schedule.shift = schedule.shift.plus(amount);
        return schedule;
    }

    protected static class EntryImpl implements DateSchedule.Entry {

        private final int mIndex;

        private final LocalDate mLocalDate;

        public EntryImpl(int index, LocalDate localDate) {
            mIndex = index;
            mLocalDate = localDate;
        }

        @Override
        public int getIndex() {
            return mIndex;
        }

        @Override
        public LocalDate getDate() {
            return mLocalDate;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            EntryImpl entry = (EntryImpl) o;
            return getIndex() == entry.getIndex() && mLocalDate.equals(entry.mLocalDate);
        }

        @Override
        public int hashCode() {
            return Objects.hash(getIndex(), mLocalDate);
        }

        @Override
        public String toString() {
            return "EntryImpl{" +
                    "mIndex = " + mIndex +
                    ", mLocalDate = " + mLocalDate +
                    '}';
        }
    }

   static class ScheduleIterator implements Iterator<DateSchedule.Entry> {

        private final DateSchedule mDateSchedule;
        private int currentIndex = 0;

        public ScheduleIterator(DateSchedule dateSchedule) {
            mDateSchedule = dateSchedule;
        }

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public DateSchedule.Entry next() {
            currentIndex++;
            return new EntryImpl(
                    currentIndex,
                    mDateSchedule.getDateFor(currentIndex)
            );
        }
    }
}
