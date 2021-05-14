package com.praktyki.backend.configuration;

import com.praktyki.backend.app.configuration.ConfigurationImpl;
import com.praktyki.backend.app.configuration.ConfigurationKeys;
import com.praktyki.backend.app.data.repositories.ConfigurationRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.*;


@SpringBootTest(classes = {
        ConfigurationImpl.class,
})
public class ConfigurationTests {

    @MockBean
    private ConfigurationRepository mConfigurationRepository;

    @Autowired
    private Configuration mConfiguration;


    @Test
    @DisplayName("Test default values")
    public void TestConfiguration() {
        Assertions.assertEquals(
                ConfigurationKeys.MAX_COMMISSION_RATE.getDefaultValue(),
                mConfiguration.get(ConfigurationKeys.MAX_COMMISSION_RATE)
        );

        Assertions.assertEquals(
                ConfigurationKeys.DEFAULT_COMMISSION_RATE.getDefaultValue(),
                mConfiguration.get(ConfigurationKeys.DEFAULT_COMMISSION_RATE)
        );

        Assertions.assertEquals(
                ConfigurationKeys.MONTH_FRAME.getDefaultValue(),
                mConfiguration.get(ConfigurationKeys.MONTH_FRAME)
        );
    }

}
