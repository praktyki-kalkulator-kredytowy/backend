package com.praktyki.backend.app.interactors;

import com.praktyki.backend.business.value.Installment;
import com.praktyki.backend.business.value.ScheduleConfiguration;
import com.praktyki.backend.business.services.schedule.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleInteractor {

    @Autowired
    private ScheduleService mScheduleService;

    public List<Installment> calculateSchedule(ScheduleConfiguration configuration) {
        return mScheduleService.createSchedule(configuration);
    }


}
