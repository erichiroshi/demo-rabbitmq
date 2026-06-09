package com.erichiroshi.demorabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class NotificacaoProducer {

    private static final String FILA = "fila.notificacao";

    public void enviar(NotificacaoDTO dto) throws Exception {
        ConnectionFactory factory = new ConnectionFactory(); // Usa localhost, guest/guest por padrão

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(FILA, true, false, false, null);

            byte[] mensagem = dto.toString().getBytes(StandardCharsets.UTF_8);
            channel.basicPublish("", FILA, null, mensagem);

            System.out.println("Mensagem enviada: " + dto);
        }
    }
}