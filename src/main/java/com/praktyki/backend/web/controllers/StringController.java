package com.praktyki.backend.web.controllers;

import com.praktyki.backend.interactors.StringConcatenationUseCase;
import com.praktyki.backend.services.exception.EntityNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StringController {

    @Autowired
    private StringConcatenationUseCase mStringConcatenationUseCase;

    @GetMapping("/api/v1/concat")
    public String concatenation(String text) throws EntityNotFound {
        return mStringConcatenationUseCase.concatenation(text);
    }

}
