package com.praktyki.backend.interactors;

import com.praktyki.backend.services.StringService;
import com.praktyki.backend.services.exception.EntityNotFound;
import org.springframework.beans.factory.annotation.Autowired;

public class StringConcatenationUseCase {

    @Autowired
    private StringService mStringService;

    public String concatenation(String text) throws EntityNotFound {

        return mStringService.concatenate(text);

    }

}
