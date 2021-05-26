package com.praktyki.backend.web.models.response;

import com.praktyki.backend.business.value.Schedule;

import java.time.LocalDate;

public class ScheduleCalculationEvent {
    public Schedule schedule;
    public LocalDate calculationDate;

    public ScheduleCalculationEvent(Schedule schedule, LocalDate calculationDate) {
        this.schedule = schedule;
        this.calculationDate = calculationDate;
    }
}
