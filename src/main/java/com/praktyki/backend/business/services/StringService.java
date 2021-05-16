package com.praktyki.backend.business.services;

import com.praktyki.backend.app.data.exceptions.EntityNotFoundException;

public class StringService {

    public String concatenate(String texta, String textb) throws EntityNotFoundException {
        return texta + textb;
    }

}
