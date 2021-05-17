package com.praktyki.backend.app.configuration;

import com.praktyki.backend.app.data.repositories.ConfigurationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


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
