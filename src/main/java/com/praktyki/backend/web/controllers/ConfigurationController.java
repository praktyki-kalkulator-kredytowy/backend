package com.praktyki.backend.web.controllers;

import com.praktyki.backend.app.configuration.ConfigurationEntryImpl;
import com.praktyki.backend.app.configuration.ConfigurationGroupKeys;
import com.praktyki.backend.app.configuration.ConfigurationKeys;
import com.praktyki.backend.app.data.repositories.ConfigurationRepository;
import com.praktyki.backend.configuration.Configuration;
import com.praktyki.backend.configuration.ConfigurationEntry;
import com.praktyki.backend.configuration.ConfigurationKey;
import com.praktyki.backend.configuration.exceptions.ConfigurationValueValidationException;
import com.praktyki.backend.web.request.models.ConfigurationEntryModel;
import com.praktyki.backend.web.response.models.ConfigurationSchemaResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class ConfigurationController {

    @Autowired
    private Configuration mConfiguration;

    @Autowired
    private ConfigurationRepository mConfigurationRepository;

    @Autowired
    private ConfigurationSchemaResponseModel mConfigurationSchemaResponseModel;

    @GetMapping("/api/v1/schedule/configuration/default")
    public ConfigurationSchemaResponseModel getConfigurationSchema() {
        return mConfigurationSchemaResponseModel;
    }

    @PostMapping("/api/v1/schedule/configuration/setConfiguration")
    public void setValue(@Valid @RequestBody ConfigurationEntryModel configurationEntryModel)
            throws ConfigurationValueValidationException {
        ConfigurationGroupKeys groupKey = ConfigurationGroupKeys.valueOf(configurationEntryModel.group);
        ConfigurationKey key = groupKey.createKey(configurationEntryModel.key);
        mConfiguration.getGroup(groupKey).save(key, configurationEntryModel.value);
    }


}
