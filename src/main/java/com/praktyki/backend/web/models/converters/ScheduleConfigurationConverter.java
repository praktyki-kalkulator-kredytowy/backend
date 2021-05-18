package com.praktyki.backend.web.models.converters;

import com.praktyki.backend.business.value.ScheduleConfiguration;
import com.praktyki.backend.web.models.request.ScheduleConfigurationModel;

public interface ScheduleConfigurationConverter {
    ScheduleConfiguration convertToScheduleConfiguration(ScheduleConfigurationModel scheduleConfigurationModel);
}
