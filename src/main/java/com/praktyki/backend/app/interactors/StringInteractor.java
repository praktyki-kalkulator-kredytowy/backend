package com.praktyki.backend.app.interactors;

import com.praktyki.backend.app.data.entities.StringEntity;
import com.praktyki.backend.app.data.exceptions.EntityNotFoundException;
import com.praktyki.backend.app.data.repositories.StringRepository;
import com.praktyki.backend.business.services.StringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class StringInteractor {

    @Autowired
    private StringService mStringService;

    @Autowired
    private StringRepository mStringRepository;

    public String concatenateStringsUseCase(String text) throws EntityNotFoundException {

        Iterator<StringEntity> stringEntityIterator = mStringRepository.findAll().iterator();

        if(!stringEntityIterator.hasNext()) throw new EntityNotFoundException("No string found");

        return mStringService.concatenate(text, stringEntityIterator.next().value);

    }

}
