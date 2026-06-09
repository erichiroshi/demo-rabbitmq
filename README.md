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
| `post/01-amqp-raw` | API AMQP pura — `channel.basicPublish`, bytes na mão | ✅ |
| `post/02-starter-amqp` | Spring AMQP Starter + Jackson2JsonMessageConverter | 🔜 |
| `post/03-padrao-mercado` | RabbitTemplate + Exchange + DLQ + @RabbitListener | 🔜 |

---

## 🛠️ Stack

- **Java 25**
- **Spring Boot 4.x** + Spring Web MVC
- **amqp-client 5.31.0** — lib oficial RabbitMQ (sem abstração Spring)
- **spring-boot-docker-compose** — sobe infraestrutura automaticamente
- **RabbitMQ 4.0** com Management UI

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
  -d '{"destinatario": "eric", "mensagem": "Olá, RabbitMQ!"}'
```

**Resposta:**
```
Notificação enviada!
```

**Console:**
```
Mensagem enviada: NotificacaoDTO[destinatario=eric, mensagem=Olá, RabbitMQ!]
Mensagem recebida: NotificacaoDTO[destinatario=eric, mensagem=Olá, RabbitMQ!]
```

---

## 🐇 RabbitMQ Management UI

Acesse `http://localhost:15672` para visualizar filas, mensagens e exchanges.

| Campo | Valor |
|-------|-------|
| Usuário | `guest` |
| Senha | `guest` |

> 💡 **Dica:** comente o `@PostConstruct` no `NotificacaoConsumer` para a aplicação não consumir as mensagens ao subir. Como a fila é durável (`durable = true`), as mensagens ficam salvas. Reative a anotação e reinicie — elas serão consumidas imediatamente.

---

## 📁 Estrutura do projeto

```
src/main/java/com/erichiroshi/demorabbitmq/
├── NotificacaoDTO.java          # Record com destinatario e mensagem
├── NotificacaoProducer.java     # Abre conexão AMQP e publica bytes
├── NotificacaoConsumer.java     # Escuta a fila em Thread Virtual
└── NotificacaoController.java   # POST /notificacoes → dispara o producer
```

---

## 📝 Sobre a série

Esta série de posts no LinkedIn mostra a evolução da integração com RabbitMQ de forma progressiva:

- **Post 1** — API AMQP pura: sem abstrações do Spring, conexão manual, serialização com `getBytes()`
- **Post 2** — Spring AMQP Starter: `RabbitTemplate` + `Jackson2JsonMessageConverter`, adeus boilerplate
- **Post 3** — Padrão mercado: Exchange, Routing Key, DLQ e `@RabbitListener` como vai para produção

Acompanhe em [linkedin.com/in/eric-hiroshi](https://linkedin.com/in/eric-hiroshi)

---

## Autor

**Eric Hiroshi**
[GitHub](https://github.com/erichiroshi) · [LinkedIn](https://linkedin.com/in/eric-hiroshi)