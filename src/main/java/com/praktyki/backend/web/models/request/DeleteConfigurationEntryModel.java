package com.praktyki.backend.web.models.request;

import com.praktyki.backend.web.validation.ValidConfigurationGroupKey;

import javax.validation.constraints.NotBlank;

public class DeleteConfigurationEntryModel {
    @NotBlank
    public String key;

    @ValidConfigurationGroupKey
    public String groupKey;
}
