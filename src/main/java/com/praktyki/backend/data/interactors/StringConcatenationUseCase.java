package com.praktyki.backend.data.interactors;

import com.praktyki.backend.data.services.StringService;
import com.praktyki.backend.data.services.exception.EntityNotFound;
import org.springframework.beans.factory.annotation.Autowired;

public class StringConcatenationUseCase {

    @Autowired
    private StringService mStringService;

    public String concatenation(String text) throws EntityNotFound {

        return mStringService.concatenate(text);

    }

}
