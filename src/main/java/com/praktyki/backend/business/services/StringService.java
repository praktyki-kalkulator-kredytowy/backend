package com.praktyki.backend.business.services;

import com.praktyki.backend.app.data.entities.StringEntity;
import com.praktyki.backend.app.data.repositories.StringRepository;
import com.praktyki.backend.app.data.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

public class StringService {

    public String concatenate(String texta, String textb) throws EntityNotFoundException {
        return texta + textb;
    }

}
