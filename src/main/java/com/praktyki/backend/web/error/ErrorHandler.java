package com.praktyki.backend.web.error;

import com.praktyki.backend.services.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private HttpServletRequest mRequest;

    private ResponseEntity<Object> createResponseEntity(ApiError error) {
        return new ResponseEntity<Object>(error, error.getStatus());
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        ApiError error = ApiError.builder()
                .setMessage("No string was found")
                .setSuggestedAction("Please insert a string to a database")
                .setStatus(HttpStatus.NOT_FOUND)
                .build();

        return createResponseEntity(error);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        ApiError error = ApiError.builder().build();

        ex.printStackTrace();

        return createResponseEntity(error);
    }



}
