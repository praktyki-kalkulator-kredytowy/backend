package com.praktyki.backend.web.models.converters;

import com.praktyki.backend.web.models.ScheduleCalculationEventDetailsModel;
import com.praktyki.backend.web.models.ScheduleCalculationEventModel;

public interface ScheduleCalculationEventConverter {

    ScheduleCalculationEventModel convertToEvent(ScheduleCalculationEventDetailsModel m);

}
