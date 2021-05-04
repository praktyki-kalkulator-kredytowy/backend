package com.praktyki.backend.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class StringEntity {

    @Id
    @GeneratedValue
    @Column(name = "strings_id")
    public Integer id;

    @Column(name = "value")
    public String value;

}
