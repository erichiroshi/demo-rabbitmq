package com.erichiroshi.demorabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoConsumer {

    @RabbitListener(queues = RabbitMQConfig.FILA)
    public void receber(Message message, @Payload NotificacaoDTO notificacaoDTO) {

        System.out.println("Raw JSON: " + new String(message.getBody()));
        System.out.println("DTO deserializado: " + notificacaoDTO);
    }
}