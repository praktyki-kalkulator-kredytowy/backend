package com.praktyki.backend.web.error;

import com.praktyki.backend.services.exception.EntityNotFound;
import com.sun.net.httpserver.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private HttpServletRequest mRequest;

    private ResponseEntity<ApiError> createResponseEntity(ApiError error) {
        return new ResponseEntity<>(error, error.getStatus());
    }


    @ExceptionHandler(EntityNotFound.class)
    public ResponseEntity<ApiError> handleEntityNotFoundException(
            EntityNotFoundException exception,
            HttpHeaders httpHeaders,
            HttpStatus status,
            WebRequest request
    ) {

        ApiError error = ApiError.builder()
                .setMessage("No string was found")
                .setSuggestedAction("Please insert a string to a database")
                .setStatus(HttpStatus.NOT_FOUND)
                .build();

        return createResponseEntity(error);

    }



}
