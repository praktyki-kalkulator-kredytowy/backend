package com.praktyki.backend.app.configuration;

import com.praktyki.backend.configuration.ConfigurationKey;

public abstract class BaseConfigurationKey implements ConfigurationKey {

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof ConfigurationKey))
            return false;
        
        return ((ConfigurationKey) obj).getName().equals(getName());
    }
}
