package com.praktyki.backend.app.configuration;

import com.praktyki.backend.configuration.ConfigurationKey;
import com.praktyki.backend.configuration.exceptions.ConfigurationValueValidationException;

public class ConfigurationValidationUtils {

    private static int convertToInt(ConfigurationKey key, String value) throws ConfigurationValueValidationException {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new ConfigurationValueValidationException(
                    "must be a whole number",
                    value,
                    key
            );
        }
    }

    private static double convertToDouble(ConfigurationKey key, String value) throws ConfigurationValueValidationException {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new ConfigurationValueValidationException(
                    "must be a number",
                    value,
                    key
            );
        } catch (Exception e) { throw new IllegalStateException(); }
    }

    public static void number(ConfigurationKey key, String value) throws ConfigurationValueValidationException {
        convertToDouble(key, value);
    }

    public static void wholeNumber(ConfigurationKey key, String value) throws ConfigurationValueValidationException {
        convertToInt(key, value);
    }

    public static void min(ConfigurationKey key, String value, double min) throws ConfigurationValueValidationException {
        if(convertToDouble(key, value) < min)
            throw new ConfigurationValueValidationException(
                    "must be at least " + min,
                    value,
                    key
            );
    }

    public static void max(ConfigurationKey key, String value, double max) throws ConfigurationValueValidationException {
        if(convertToDouble(key, value) > max)
            throw new ConfigurationValueValidationException(
                    "must be at most " + max,
                    value,
                    key
            );
    }

    public static void within(ConfigurationKey key, String value, double min, double max) throws ConfigurationValueValidationException {
        min(key, value, min);
        max(key, value, max);
    }


}
