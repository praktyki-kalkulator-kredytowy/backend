package com.praktyki.backend.app.configuration;

public abstract class BaseConfigurationKey implements ConfigurationKey {

    @Override
    public int hashCode() {
        return getKey().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof ConfigurationKey))
            return false;
        
        return ((ConfigurationKey) obj).getKey().equals(getKey());
    }
}
