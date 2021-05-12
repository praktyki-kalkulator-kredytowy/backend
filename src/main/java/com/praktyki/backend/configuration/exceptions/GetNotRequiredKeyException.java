package com.praktyki.backend.configuration.exceptions;

public class GetNotRequiredKeyException extends ConfigurationException {

    GetNotRequiredKeyException(String key) {
        super("Attempted to get the value of a key '" + key + "' that was not required.");
    }

}
