package com.praktyki.backend.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {

    public static String EXCHANGE_NAME = "schedules";

    @Bean
    public FanoutExchange getFanOut() {
        return new FanoutExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue getAnonymousQueue() {
        return new AnonymousQueue();
    }

    @Bean
    public Binding getBinding(FanoutExchange fanoutExchange, Queue anonymousQueue) {
        return BindingBuilder.bind(anonymousQueue).to(fanoutExchange);
    }

    @Bean
    public MessageConverter getJsonMassageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }


    public AmqpTemplate getAmqpTemplate(RabbitTemplate template, Jackson2JsonMessageConverter converter) {
        template.setMessageConverter(converter);
        return template;
    }
}
