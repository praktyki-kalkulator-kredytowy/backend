package com.praktyki.backend.web.models.converters;

import com.praktyki.backend.business.value.ScheduleConfiguration;
import com.praktyki.backend.web.models.ScheduleConfigurationModel;

public interface ScheduleConfigurationConverter {
    ScheduleConfiguration convertToScheduleConfiguration(ScheduleConfigurationModel scheduleConfigurationModel);

    ScheduleConfigurationModel convertToScheduleConfigurationModel(ScheduleConfiguration c);
}
