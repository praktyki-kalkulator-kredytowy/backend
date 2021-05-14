package com.praktyki.backend.configuration.exceptions;

import com.praktyki.backend.configuration.ConfigurationKey;

public class ConfigurationKeyDeletionException extends ConfigurationException {

    public ConfigurationKeyDeletionException(ConfigurationKey key, String message) {
        super("Key " + key.getName() + " could not be removed - " + message);
    }
}
