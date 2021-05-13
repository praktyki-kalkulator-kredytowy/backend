package com.praktyki.backend.web.response.models;

import com.praktyki.backend.app.configuration.ConfigurationGroupKeys;
import com.praktyki.backend.app.configuration.ConfigurationKeys;
import com.praktyki.backend.configuration.ConfigurationGroup;
import com.praktyki.backend.configuration.ConfigurationGroupKey;
import com.praktyki.backend.configuration.ConfigurationKey;

import java.util.*;
import java.util.stream.Collectors;

public class ConfigurationSchemaResponseModel {

    public List<ConfigurationGroupSchema> groups = new LinkedList<>();

    public ConfigurationSchemaResponseModel() {
        Arrays.stream(ConfigurationGroupKeys.values()).forEach(k -> {
            if(k == ConfigurationGroupKeys.DEFAULT)
                groups.add(new ConfigurationGroupSchema(
                        ConfigurationGroupKeys.DEFAULT,
                        Arrays.stream(ConfigurationKeys.values()).map(ConfigurationKeySchema::new)
                                .collect(Collectors.toList())
                ));
            else groups.add(new ConfigurationGroupSchema(k, Collections.emptyList()));

        });
    }


    public static class ConfigurationGroupSchema  {

        public String groupKey;
        public String displayName;
        public String description;
        List<ConfigurationKeySchema> entries;

        public ConfigurationGroupSchema(ConfigurationGroupKey key, List<ConfigurationKeySchema> entries) {
            this.groupKey = key.getKey();
            this.displayName = key.getDisplayName();
            this.description = key.getDescription();
            this.entries = entries;
        }
    }

    public static class ConfigurationKeySchema  {
        public String name;
        public String displayName;
        public String defaultValue;
        public String description;

        public ConfigurationKeySchema(ConfigurationKey key) {
            this.name = key.getName();
            this.displayName = key.getDisplayName();
            this.defaultValue = key.getDefaultValue();
            this.description = key.getDescription();
        }
    }

}
