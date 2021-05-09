package com.praktyki.backend.web.controllers;

import com.praktyki.backend.interactors.StringConcatenationUseCase;
import com.praktyki.backend.services.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@CrossOrigin
@RestController
public class StringController {

    @Autowired
    private StringConcatenationUseCase mStringConcatenationUseCase;

    @GetMapping("/api/v1/concat/")
    public String concatenation(
            @Valid
            @NotBlank(message = "Please specify a string")
            @RequestParam(name = "value") String text
    ) throws EntityNotFoundException {
        return mStringConcatenationUseCase.concatenation(text);
    }

}
