package com.praktyki.backend.web.controllers;

import com.praktyki.backend.interactors.StringConcatenationUseCase;
import com.praktyki.backend.services.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class StringController {

    @Autowired
    private StringConcatenationUseCase mStringConcatenationUseCase;

    @GetMapping("/api/v1/concat/{strValue}")
    public String concatenation(@PathVariable(name = "strValue") String text) throws EntityNotFoundException {
        return mStringConcatenationUseCase.concatenation(text);
    }

}
