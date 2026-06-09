package com.erichiroshi.demorabbitmq;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notificacoes")
public class NotificacaoController {

    private final NotificacaoProducer producer;

    public NotificacaoController(NotificacaoProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public ResponseEntity<String> enviar(@RequestBody NotificacaoDTO dto) throws Exception {
        producer.enviar(dto);
        return ResponseEntity.ok("Notificação enviada!");
    }
}