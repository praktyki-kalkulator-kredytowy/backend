package com.praktyki.backend.web.request.models;

import javax.validation.constraints.NotBlank;

public class ConfigurationEntryModel {

    @NotBlank(message = "Please provide a key")
    public String key;

    @NotBlank(message = "Please provide a value")
    public String value;

    public String group;

}
