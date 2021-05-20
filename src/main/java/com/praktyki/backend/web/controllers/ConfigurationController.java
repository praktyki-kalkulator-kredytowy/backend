package com.praktyki.backend.web.controllers;

import com.praktyki.backend.app.configuration.*;
import com.praktyki.backend.app.data.repositories.ConfigurationRepository;
import com.praktyki.backend.configuration.Configuration;
import com.praktyki.backend.configuration.ConfigurationEntry;
import com.praktyki.backend.configuration.ConfigurationGroupKey;
import com.praktyki.backend.configuration.ConfigurationKey;
import com.praktyki.backend.app.configuration.exceptions.ConfigurationValueValidationException;
import com.praktyki.backend.web.models.request.ConfigurationEntryModel;
import com.praktyki.backend.web.models.request.DeleteConfigurationEntryModel;
import com.praktyki.backend.web.models.response.ConfigurationSchemaResponseModel;
import com.praktyki.backend.web.models.response.ScheduleConfigurationConfiguration;
import com.praktyki.backend.web.validation.ValidConfigurationGroupKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.List;

@CrossOrigin
@RestController
@Validated
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

    @PostMapping("/api/v1/schedule/configuration")
    public void setValue(@Valid @RequestBody ConfigurationEntryModel configurationEntryModel)
            throws ConfigurationValueValidationException {
        ConfigurationGroupKeys groupKey = ConfigurationGroupKeys.valueOf(configurationEntryModel.group);
        ConfigurationKey key = groupKey.createKey(configurationEntryModel.key);
        mConfiguration.getGroup(groupKey).save(key, configurationEntryModel.value);
    }

    @DeleteMapping("/api/v1/schedule/configuration")
    public void removeKey(@Valid @RequestBody DeleteConfigurationEntryModel model) {
        ConfigurationGroupKey group = ConfigurationGroupKeys.valueOf(model.groupKey);
        mConfiguration.getGroup(group).remove(group.createKey(model.key));
    }

    @GetMapping("/api/v1/schedule/configuration/scheduleConfiguration")
    public ScheduleConfigurationConfiguration getScheduleConfigurationConfiguration() {
        return new ScheduleConfigurationConfiguration(mConfiguration);
    }

    @GetMapping("/api/v1/schedule/configuration/group")
    public Collection<ConfigurationEntry> getEntriesForGroup(
            @Valid @ValidConfigurationGroupKey @RequestParam("groupKey") String groupKey
    ) {
        return mConfiguration.getGroup(ConfigurationGroupKeys.valueOf(groupKey))
                .getEntries();
    }

    @GetMapping("/api/v1/schedule/configuration")
    public ConfigurationEntry getConfiguration(
            @Valid @ValidConfigurationGroupKey @RequestParam("groupKey") String group,
            @NotBlank(message = "key must not be empty") @RequestParam("key") String key
    ) {
        ConfigurationGroupKey groupKey = ConfigurationGroupKeys.valueOf(group);
        return mConfiguration
                .getGroup(groupKey)
                .getEntry(groupKey.createKey(key));
    }



}
