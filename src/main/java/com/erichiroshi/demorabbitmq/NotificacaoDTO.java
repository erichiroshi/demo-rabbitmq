package com.erichiroshi.demorabbitmq;

public record NotificacaoDTO(
        String destinatario,
        String mensagem
) {
}