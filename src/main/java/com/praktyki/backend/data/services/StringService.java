package com.praktyki.backend.data.services;

import com.praktyki.backend.data.entities.StringEntity;
import com.praktyki.backend.data.repositories.StringRepository;
import com.praktyki.backend.data.services.exception.EntityNotFound;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;

public class StringService {

    @Autowired
    private StringRepository mStringRepository;

    public String concatenate(String text) throws EntityNotFound {

        Iterator<StringEntity> stringEntityIterator = mStringRepository.findAll().iterator();

        if(!stringEntityIterator.hasNext()) throw new EntityNotFound("No string found");

        return text + stringEntityIterator.next().value;

    }

}
