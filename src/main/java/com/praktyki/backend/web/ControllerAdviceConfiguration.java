package com.praktyki.backend.web;

import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class ControllerAdviceConfiguration {
    @InitBinder
    private void initDirectFieldAccess(DataBinder dataBinder) {
        dataBinder.initDirectFieldAccess();
    }
}
