package com.praktyki.backend.services;

import com.praktyki.backend.data.entities.StringEntity;
import com.praktyki.backend.data.repositories.StringRepository;
import com.praktyki.backend.services.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class StringService {

    @Autowired
    private StringRepository mStringRepository;

    public String concatenate(String text) throws EntityNotFoundException {

        Iterator<StringEntity> stringEntityIterator = mStringRepository.findAll().iterator();

        if(!stringEntityIterator.hasNext()) throw new EntityNotFoundException("No string found");

        return text + stringEntityIterator.next().value;

    }

}
