package com.praktyki.backend.app.data.entities;

import javax.persistence.*;

@Entity
@Table(name = "strings")
public class StringEntity {

    @Id
    @GeneratedValue
    @Column(name = "strings_id")
    public Integer id;

    @Column(name = "value")
    public String value;

}
