package com.praktyki.backend.web.controllers;

import com.praktyki.backend.app.interactors.StringInteractor;
import com.praktyki.backend.app.data.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@CrossOrigin
@Validated
@RestController
public class StringController {

    @Autowired
    private StringInteractor mStringInteractor;

    @GetMapping("/api/v1/concat")
    public String concatenation(
            @NotBlank(message = "Please specify a string")
            @RequestParam(name = "value") String text
    ) throws EntityNotFoundException {
        return mStringInteractor.concatenateStringsUseCase(text);
    }

}
