# demo-rabbitmq

![Java](https://img.shields.io/badge/Java-25-orange?style=flat-square&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.x-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-4.0-FF6600?style=flat-square&logo=rabbitmq&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?style=flat-square&logo=docker&logoColor=white)

Série de exemplos progressivos de mensageria com RabbitMQ em Java 25 + Spring Boot 4.

O objetivo é mostrar a evolução desde a API AMQP mais básica até a configuração padrão de mercado — cada branch representa uma etapa da série de posts no LinkedIn.

---

## 🗺️ Roadmap

| Branch | Descrição | Status |
|--------|-----------|--------|
| [`post/01-amqp-raw`](https://github.com/erichiroshi/demo-rabbitmq/tree/post/01-amqp-raw) | API AMQP pura — `channel.basicPublish`, bytes na mão | ✅ |
| [`post/02-starter-amqp`](https://github.com/erichiroshi/demo-rabbitmq/tree/post/02-starter-amqp) | Spring AMQP Starter + `JacksonJsonMessageConverter` | ✅ |
| [`post/03-padrao-mercado`](https://github.com/erichiroshi/demo-rabbitmq/tree/post/03-padrao-mercado) | Exchange, DLX, DLQ, Binding e `@RabbitListener` | ✅ |

---

## 🛠️ Stack

- **Java 25**
- **Spring Boot 4.x** + Spring Web MVC
- **spring-boot-starter-amqp** — abstração Spring sobre o protocolo AMQP
- **JacksonJsonMessageConverter** — serialização/deserialização automática para JSON
- **spring-boot-docker-compose** — sobe infraestrutura automaticamente
- **RabbitMQ 4.0** com Management UI

> A branch `post/01-amqp-raw` usa `amqp-client 5.31.0` puro, sem abstração Spring — ideal para entender o que acontece por baixo.

---

## ⚙️ Pré-requisitos

- Java 25+
- Docker Desktop rodando

> Não é necessário instalar RabbitMQ manualmente. O Spring Boot sobe o container automaticamente via `compose.yml`.

---

## 🚀 Quick Start

```bash
# Clone o repositório
git clone https://github.com/erichiroshi/demo-rabbitmq.git
cd demo-rabbitmq

# Suba a aplicação (RabbitMQ sobe automaticamente via Docker Compose)
./gradlew bootRun
```

Ao iniciar, o Spring Boot detecta o `compose.yml` e sobe o container do RabbitMQ antes da aplicação.

### Disparar uma notificação

```bash
curl -X POST http://localhost:8080/notificacoes \
  -H "Content-Type: application/json" \
  -d '{"destinatario": "eric", "mensagem": "Ola RabbitMQ"}'
```

**Resposta:**
```
Notificação enviada!
```

**Console — mensagem processada com sucesso:**
```
Mensagem enviada:       NotificacaoDTO[destinatario=eric, mensagem=Ola RabbitMQ]
Mensagem recebida:      NotificacaoDTO[destinatario=eric, mensagem=Ola RabbitMQ]
Processado com sucesso: Ola RabbitMQ
```

**Console — mensagem que falha e vai pra DLQ:**
```bash
curl -X POST http://localhost:8080/notificacoes \
  -H "Content-Type: application/json" \
  -d '{"destinatario": "erro", "mensagem": "isso vai falhar"}'
```
```
⚠️ DLQ — mensagem com falha: NotificacaoDTO[destinatario=erro, mensagem=isso vai falhar]
```

---

## 🐇 RabbitMQ Management UI

Acesse `http://localhost:15672` para visualizar filas, mensagens e exchanges.

| Campo | Valor |
|-------|-------|
| Usuário | `guest` |
| Senha | `guest` |

> 💡 **Dica:** quer visualizar a mensagem na fila antes de consumir? Comente o `@RabbitListener` no `NotificacaoConsumer`. Como a fila é durável (`durable = true`), a mensagem fica salva. Reative a anotação e reinicie — ela é consumida na hora.
>
> Para testar o fluxo da DLQ, envie `{ "destinatario": "erro", "mensagem": "teste" }` — a mensagem será rejeitada e aparecerá em `fila.notificacao.dlq`.

---

## 📁 Estrutura do projeto

```
src/main/java/com/erichiroshi/demorabbitmq/
├── NotificacaoDTO.java          # Record com destinatario e mensagem
├── RabbitMQConfig.java          # Exchange, DLX, filas, bindings, converter e RabbitTemplate
├── NotificacaoProducer.java     # rabbitTemplate.convertAndSend(exchange, routingKey, dto)
├── NotificacaoConsumer.java     # @RabbitListener na fila principal e na DLQ
└── NotificacaoController.java   # POST /notificacoes → dispara o producer
```

---

## 📝 Sobre a série

Esta série de posts no LinkedIn mostra a evolução da integração com RabbitMQ de forma progressiva:

- **Post 1** — API AMQP pura: sem abstrações do Spring, conexão manual, serialização com `getBytes()`
- **Post 2** — Spring AMQP Starter: `RabbitTemplate` + `JacksonJsonMessageConverter`, adeus boilerplate
- **Post 3** — Exchange, Routing Key, Binding e DLQ: vocabulário e estrutura do mercado
- **Post 4** — Consumer + DLQ em ação: erro simulado, `default-requeue-rejected: false` e mensagem que falha tem destino
- **Post 5** — Conclusão: casos de uso reais e quando faz sentido usar mensageria

Acompanhe em [linkedin.com/in/eric-hiroshi](https://linkedin.com/in/eric-hiroshi)

---

## Autor

**Eric Hiroshi**
[GitHub](https://github.com/erichiroshi) · [LinkedIn](https://linkedin.com/in/eric-hiroshi)
