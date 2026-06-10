package com.erichiroshi.demorabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "notificacao.exchange";
    public static final String FILA = "fila.notificacao";
    public static final String ROUTING_KEY = "notificacao.rk";
    public static final String FILA_DLQ = "fila.notificacao.dlq";
    public static final String FILA_DLX = "fila.notificacao.dlx";

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public DirectExchange filaDeadLetterExchange() {
        return new DirectExchange(FILA_DLX);
    }

    // Fila principal — aponta para a DLQ em caso de erro
    @Bean
    public Queue queue() {
        return QueueBuilder.durable(FILA)
                .withArgument("x-dead-letter-exchange", FILA_DLX)
                .withArgument("x-dead-letter-routing-key", FILA_DLQ)
                .build();
    }

    // Fila de erro
    @Bean
    public Queue queueDlq() {
        return QueueBuilder.durable(FILA_DLQ).build();
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(ROUTING_KEY);
    }

    @Bean
    public Binding filaDlqBinding() {
        return BindingBuilder.bind(queueDlq())
                .to(filaDeadLetterExchange())
                .with(FILA_DLQ);
    }

    @Bean
    public JacksonJsonMessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         JacksonJsonMessageConverter messageConverter) {

        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }
}