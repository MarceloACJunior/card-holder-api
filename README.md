# card-holder-api

Microsserviço responsável pelo gerenciamento de portadores de cartão de crédito e seus cartões no ecossistema JazzTech.

## Visão Geral

Permite criar e gerenciar **CardHolders** (portadores) e seus **cartões de crédito**. A criação de um CardHolder exige uma análise de crédito aprovada, validada via `credit-analysis-api`. O limite é controlado individualmente por cartão, respeitando o limite total do portador.

## Stack

- **Java 17** + **Spring Boot 3.1.0**
- **Spring Cloud OpenFeign** — comunicação com `credit-analysis-api`
- **Spring Data JPA** + **PostgreSQL**
- **MapStruct** | **Lombok** | **JUnit 5** + **Mockito**

## Rodando

### Opção 1 — Docker Compose (recomendado)

Sobe o banco e os 3 microsserviços de uma vez. Os repositórios `client-api` e `credit-analysis-api` devem estar clonados como pastas irmãs deste:

```
../client-api/
../credit-analysis-api/
../card-holder-api/   ← este repositório
```

```bash
docker-compose up --build
```

**Parar tudo:**
```bash
docker-compose down
```

**Parar e apagar os dados do banco:**
```bash
docker-compose down -v
```

Na primeira execução o Maven baixa as dependências dentro dos containers — pode levar alguns minutos.

### Opção 2 — Local

**Pré-requisitos:** Java 17+, Docker, `credit-analysis-api` rodando na porta 9001.

```bash
# 1. Subir o banco PostgreSQL com um único container (porta 5432)
docker run -d --name postgres \
  -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=senha123 -e POSTGRES_DB=postgres \
  -p 5432:5432 postgres:16-alpine

# 2. Criar database e tabelas
docker exec postgres psql -U admin -d postgres -c "CREATE DATABASE carddb;"
docker exec -i postgres psql -U admin -d carddb < database/ddl.sql

# 3. Iniciar a aplicação
./mvnw spring-boot:run      # Linux/Mac
mvnw.cmd spring-boot:run    # Windows

# Parar o banco
docker stop postgres && docker rm postgres
```

A API fica disponível em `http://localhost:9002`.

## Endpoints

| Método | Endpoint | Status | Descrição |
|--------|----------|--------|-----------|
| `POST` | `/v1.0/card-holders` | 201 | Criar CardHolder |
| `GET` | `/v1.0/card-holders` | 200 | Listar por status (`?status=ACTIVE`) |
| `GET` | `/v1.0/card-holders/{id}` | 200 | Buscar CardHolder por ID |
| `POST` | `/v1.0/card-holders/{id}/cards` | 201 | Emitir cartão de crédito |
| `GET` | `/v1.0/card-holders/{id}/cards` | 200 | Listar cartões do portador |
| `GET` | `/v1.0/card-holders/{id}/cards/{cardId}` | 200 | Buscar cartão por ID |
| `PATCH` | `/v1.0/card-holders/{id}/cards/{cardId}` | 200 | Atualizar limite do cartão |

### Exemplo — Criar CardHolder

```http
POST /v1.0/card-holders
Content-Type: application/json

{
  "clientId": "uuid-do-cliente",
  "creditAnalysisId": "uuid-da-analise-aprovada",
  "bankAccount": {
    "account": "12345678-9",
    "agency": "0001",
    "bankCode": "341"
  }
}
```

### Exemplo — Emitir cartão

```http
POST /v1.0/card-holders/{cardHolderId}/cards
Content-Type: application/json

{
  "cardHolderId": "uuid-do-card-holder",
  "limit": 1500.00
}
```

## Geração do cartão

- **Número:** 16 dígitos, formato Visa (começa com `4`)
- **CVV:** entre 100 e 999
- **Vencimento:** 5 anos a partir da emissão
- **Limite disponível:** limite total do portador menos a soma dos limites dos cartões ativos

## Banco de dados

Schema em `database/ddl.sql`. PostgreSQL na porta `5432`, usuário `admin`, senha `senha123`, banco `carddb`.

## Testes

```bash
./mvnw test                          # Todos os testes
./mvnw test -Dtest=NomeDaClasse      # Teste específico
./mvnw verify                        # Testes + relatório JaCoCo (target/site/jacoco/)
./mvnw checkstyle:check              # Verificar estilo
```

## Microsserviços relacionados

| Serviço | Porta | Função |
|---------|-------|--------|
| `client-api` | 8080 | Cadastro de clientes |
| `credit-analysis-api` | 9001 | Análise de crédito |
| `card-holder-api` | 9002 | **Este serviço** |
