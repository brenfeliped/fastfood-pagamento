# 🍔 FastFood - Microserviço de Pagamento

Microserviço responsável pelo gerenciamento de pagamentos do sistema Fast Food, desenvolvido para o **Desafio SOAT Tech Challenge**, utilizando **Arquitetura Hexagonal** com Java e Spring Boot.

---

## ✅ Tecnologias utilizadas

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **PostgreSQL**
- **Docker e Docker Compose**
- **Swagger (SpringDoc OpenAPI)**
- **Spring Kafka** (Mensageria)
- **JUnit 5 & Mockito** (Testes Unitários)

---

## ✅ Arquitetura

Este projeto segue a **Arquitetura Hexagonal**, organizando as responsabilidades em:

- **Domain:** Entidades de negócio (Pagamento) e interfaces de repositório.
- **Application:** Casos de uso (Services) e regras de negócio.
- **Infrastructure:**
    - **Controller:** Adaptadores de entrada (REST APIs).
    - **Persistence:** Implementação de repositórios com JPA.
    - **Messaging:** Produtores e consumidores Kafka.
- **Configs:** Configurações do projeto (Swagger, etc).

---

## ✅ Funcionalidades

- **Processamento de Pagamento:** Recebe solicitações de pagamento e processa via Webhook.
- **Consulta de Pagamentos:**
    - Por ID do pagamento.
    - Por ID do pedido.
    - Por Status (APROVADO, REPROVADO, PENDENTE).
- **Integração via Kafka:**
    - Consome eventos de criação de pedido.
    - Publica eventos de atualização de status de pagamento.

---

## ✅ Pré-requisitos

- [Java 17+](https://adoptium.net/)
- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/)
- [Maven](https://maven.apache.org/)

---

## ✅ Como executar o projeto

### 1. Subir a infraestrutura (Banco de Dados e Kafka)

Utilize o Docker Compose para subir os containers necessários:

```bash
docker-compose up -d
```

### 2. Executar a aplicação

Você pode rodar a aplicação via Maven ou pela sua IDE de preferência.

**Via Maven:**
```bash
./mvnw spring-boot:run
```

### 3. Acessar a aplicação

- **Swagger UI (Documentação):** [http://localhost:8080/fastfood-pagamento/swagger-ui/index.html](http://localhost:8080/fastfood-pagamento/swagger-ui/index.html)
- **API Base URL:** `http://localhost:8080/fastfood-pagamento/api/pagamentos`

---

## ✅ Testes

O projeto possui uma cobertura de testes unitários superior a 80%. Para executar os testes e verificar a cobertura:

```bash
./mvnw clean verify
```

O relatório de cobertura (JaCoCo) será gerado em `target/site/jacoco/index.html`.

---

## 📩 Endpoints Principais

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/api/pagamentos` | Cria um novo pagamento (geralmente interno ou teste) |
| `GET` | `/api/pagamentos/{id}` | Busca pagamento por ID |
| `GET` | `/api/pagamentos/pedidoid/{id}` | Busca pagamento pelo ID do Pedido |
| `GET` | `/api/pagamentos/status/{status}` | Lista pagamentos por status |
| `POST` | `/api/pagamentos/webhook` | Webhook para receber confirmação de pagamento |

---
