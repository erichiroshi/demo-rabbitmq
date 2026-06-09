package com.erichiroshi.demorabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoProducer {

    private final RabbitTemplate rabbitTemplate;

    public NotificacaoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviar(NotificacaoDTO dto) {

        rabbitTemplate.convertAndSend(RabbitMQConfig.FILA, dto);
        System.out.println("Mensagem enviada: " + dto);
    }

}