package com.praktyki.backend.web.models.converters;

import com.praktyki.backend.business.value.Schedule;
import com.praktyki.backend.web.models.ScheduleModel;



public interface ScheduleConverter {
    ScheduleModel convertToModel(Schedule schedule);
}
