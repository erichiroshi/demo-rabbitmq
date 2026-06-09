package com.erichiroshi.demorabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoConsumer {

    private static final String FILA = "fila.notificacao";

    @PostConstruct
    public void iniciar() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(FILA, true, false, false, null);

        System.out.println("Aguardando mensagens...");

        Thread.ofVirtual().start(() -> {
            try {
                channel.basicConsume(FILA, true, (tag, delivery) -> {
                    String mensagem = new String(delivery.getBody());
                    System.out.println("Mensagem recebida: " + mensagem);
                }, tag -> {
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}