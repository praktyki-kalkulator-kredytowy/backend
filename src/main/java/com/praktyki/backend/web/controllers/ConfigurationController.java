package com.praktyki.backend.web.controllers;

import com.praktyki.backend.app.configuration.ConfigurationEntryImpl;
import com.praktyki.backend.app.configuration.ConfigurationGroupKeys;
import com.praktyki.backend.app.configuration.ConfigurationKeys;
import com.praktyki.backend.app.data.repositories.ConfigurationRepository;
import com.praktyki.backend.configuration.*;
import com.praktyki.backend.configuration.exceptions.ConfigurationValueValidationException;
import com.praktyki.backend.web.request.models.ConfigurationEntryModel;
import com.praktyki.backend.web.response.models.ConfigurationResponseModel;
import com.praktyki.backend.web.response.models.ConfigurationSchemaResponseModel;
import com.praktyki.backend.web.response.models.ScheduleConfigurationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class ConfigurationController {

    @Autowired
    private Configuration mConfiguration;

    @Autowired
    private ConfigurationRepository mConfigurationRepository;

    @Autowired
    private ConfigurationSchemaResponseModel mConfigurationSchemaResponseModel;

    @GetMapping("/api/v1/schedule/configuration/schema")
    public ConfigurationSchemaResponseModel getConfigurationSchema() {
        return mConfigurationSchemaResponseModel;
    }

    @PostMapping("/api/v1/schedule/configuration/set")
    public void setValue(@Valid @RequestBody ConfigurationEntryModel configurationEntryModel)
            throws ConfigurationValueValidationException {
        ConfigurationGroupKeys groupKey = ConfigurationGroupKeys.valueOf(configurationEntryModel.group);
        ConfigurationKey key = groupKey.createKey(configurationEntryModel.key);
        mConfiguration.getGroup(groupKey).save(key, configurationEntryModel.value);
    }

    @GetMapping("/api/v1/schedule/configuration/scheduleConfiguration")
    public ScheduleConfigurationConfiguration getScheduleConfigurationConfiguration() {
        return new ScheduleConfigurationConfiguration(mConfiguration);
    }


}
