package com.praktyki.backend.app.data.entities;

import javax.persistence.*;

@Entity
@Table(name = "configuration")
public class ConfigurationEntryEntity {

    @Id
    @GeneratedValue
    @Column(name = "configuration_id")
    public int id;

    @Column(name = "configuration_key")
    public String key;

    @Column(name = "configuration_value")
    public String value;

    @Column(name = "configuration_group")
    public String group;

    public ConfigurationEntryEntity() {}

    public ConfigurationEntryEntity(int id, String key, String value, String group) {
        this.id = id;
        this.key = key;
        this.value = value;
        this.group = group;
    }
}
