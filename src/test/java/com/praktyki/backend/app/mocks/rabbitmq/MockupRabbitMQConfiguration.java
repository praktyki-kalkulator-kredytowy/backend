package com.praktyki.backend.app.mocks.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.*;

@TestConfiguration
public class MockupRabbitMQConfiguration {

    @Bean
    public AmqpTemplate getMockTemplate() {
        return mock(AmqpTemplate.class);
    }
}
