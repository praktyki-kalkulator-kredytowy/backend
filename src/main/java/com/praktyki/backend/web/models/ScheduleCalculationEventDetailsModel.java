package com.praktyki.backend.web.models;

import java.time.LocalDate;
import java.util.Objects;

public class ScheduleCalculationEventDetailsModel {
    public int id;
    public LocalDate calculationDate;
    public ScheduleModel schedule;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleCalculationEventDetailsModel that = (ScheduleCalculationEventDetailsModel) o;
        return id == that.id && calculationDate.equals(that.calculationDate) && schedule.equals(that.schedule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, calculationDate, schedule);
    }
}
