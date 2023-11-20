package com.discovery.msuser.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Bean
    public DirectExchange ucbExchange() {
        return new DirectExchange("ucbExchange");
    }

   @Bean
   public Queue gamification1Queue() {
       return QueueBuilder.durable("gamification1.queue")
               .build();
   }

   @Bean
   public Queue gamification2Queue() {
        return QueueBuilder.durable("gamification2.queue")
                .build();
   }


    @Bean
    public Queue reportsQueue() {
        return QueueBuilder.durable("reports.queue")
                .build();
    }

    @Bean
    public Binding bindingGamification1(DirectExchange ucbExchange, Queue gamification1Queue) {
        return BindingBuilder.bind(gamification1Queue)
                .to(ucbExchange)
                .with("game.routingKey");
    }

    @Bean
    public Binding bindingGamification2(DirectExchange ucbExchange, Queue gamification2Queue) {
        return BindingBuilder.bind(gamification2Queue)
                .to(ucbExchange)
                .with("game.routingKey");
    }

    @Bean
    public Binding bindingReport(DirectExchange ucbExchange, Queue reportsQueue) {
        return BindingBuilder.bind(reportsQueue)
                .to(ucbExchange)
                .with("reports.routingKey");
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

}
