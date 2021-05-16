package com.praktyki.backend.web.error;

import com.praktyki.backend.app.data.exceptions.EntityNotFoundException;
import com.praktyki.backend.business.services.exceptions.NoInsuranceRateForAgeException;
import com.praktyki.backend.configuration.exceptions.ConfigurationGroupValidationException;
import com.praktyki.backend.configuration.exceptions.ConfigurationValueValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    private static Logger log = LogManager.getLogger();

    @Autowired
    private HttpServletRequest mRequest;

    private ResponseEntity<Object> createResponseEntity(ApiError error) {
        return new ResponseEntity<Object>(error, error.getStatus());
    }

    private String stackTraceToString(Throwable t) {
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);

        t.printStackTrace(printWriter);
        printWriter.flush();

        return writer.toString();
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


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ApiSubError> subErrors = ex.getBindingResult().getFieldErrors().stream().map( e -> new ApiSubError(
                "Validation failed for: " + e.getRejectedValue() + " - " + e.getDefaultMessage(),
                "Check the rejected value against the error"
        )).collect(Collectors.toList());

        return createResponseEntity(ApiError.builder()
                .setSubErrors(subErrors)
                .setMessage("Validation failed")
                .setSuggestedAction("See validation errors for more info")
                .setStatus(HttpStatus.BAD_REQUEST)
                .build());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        List<ApiSubError> subErrors = ex.getConstraintViolations().stream().map(v -> new ApiSubError(
                "Validation failed for " + v.getInvalidValue() + " - " + v.getMessage(),
                "Check rejected value against the error"
        )).collect(Collectors.toList());

        return createResponseEntity(ApiError.builder()
                .setSubErrors(subErrors)
                .setMessage("Validation failed")
                .setSuggestedAction("See validation errors for more info")
                .setStatus(HttpStatus.BAD_REQUEST)
                .build());
    }

    @ExceptionHandler(ConfigurationValueValidationException.class)
    public ResponseEntity<Object> handleConfigurationValueValidationException(ConfigurationValueValidationException ex) {
        return createResponseEntity(ApiError.builder()
                .setStatus(HttpStatus.BAD_REQUEST)
                .setMessage(ex.getMessage())
                .setSuggestedAction("Please fix the configuration and try again")
                .build()
        );
    }

    @ExceptionHandler(ConfigurationGroupValidationException.class)
    public ResponseEntity<Object> handleConfigurationGroupValidationException(ConfigurationValueValidationException ex) {
        return createResponseEntity(ApiError.builder()
                .setStatus(HttpStatus.BAD_REQUEST)
                .setMessage(ex.getMessage())
                .setSuggestedAction("Please contact the system administrator")
                .build()
        );
    }

    @ExceptionHandler(NoInsuranceRateForAgeException.class)
    public ResponseEntity<Object> handleNoInsuranceRateForAgeException(NoInsuranceRateForAgeException ex) {
        return createResponseEntity(ApiError.builder()
                .setStatus(HttpStatus.NOT_FOUND)
                .setMessage(ex.getMessage())
                .setSuggestedAction("Configure insurance rate")
                .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        ApiError error = ApiError.builder().build();

        log.error(stackTraceToString(ex));

        return createResponseEntity(error);
    }


}
