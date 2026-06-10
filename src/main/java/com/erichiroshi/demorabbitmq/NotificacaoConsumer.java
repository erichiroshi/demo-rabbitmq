package com.erichiroshi.demorabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoConsumer {

    @RabbitListener(queues = RabbitMQConfig.FILA)
    public void receber(@Payload NotificacaoDTO dto) {

        if ("erro".equalsIgnoreCase(dto.destinatario())) {
            throw new RuntimeException("Erro simulado — destinatário inválido: " + dto.destinatario());
        }

        System.out.println("Mensagem recebida:      " + dto);
        System.out.println("Processado com sucesso: " + dto.mensagem());
    }

    @RabbitListener(queues = RabbitMQConfig.FILA_DLQ)
    public void receberDlq(@Payload NotificacaoDTO dto) {
        System.out.println("⚠️ DLQ — mensagem com falha: " + dto);
    }
}