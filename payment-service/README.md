# Payment Service

A Spring Boot REST API that demonstrates a simple payment microservice. It stores payment records in PostgreSQL. Payment success is simulated; this project does not connect to a real payment provider.

## Technology stack

- Java 17
- Spring Boot 3.4.4
- Maven
- Spring Web
- Spring Data JPA
- PostgreSQL
- Bean Validation
- Spring Boot Actuator

## Prerequisites

- Java 17+
- Maven 3.6+
- PostgreSQL

## Database setup

Create a database and user in PostgreSQL:

```sql
CREATE USER paymentuser WITH PASSWORD 'paymentpass';
CREATE DATABASE paymentdb OWNER paymentuser;
```

For a real environment, use a dedicated secret manager and do not commit credentials.

## Configuration

The defaults are in `src/main/resources/application.properties`. You can override them with environment variables:

- `DB_URL` (default: `jdbc:postgresql://localhost:5432/paymentdb`)
- `DB_USERNAME` (default: `paymentuser`)
- `DB_PASSWORD` (default: `paymentpass`)
- `SERVER_PORT` (default: `8080`)

## Run locally

```bash
mvn clean test
mvn spring-boot:run
```

The service listens on `http://localhost:8080`.

## API endpoints

### Create a payment

`POST /api/payments`

```json
{
  "customerId": "customer-101",
  "amount": 1500.00,
  "currency": "INR"
}
```

The demo service records the payment with status `SUCCESS` to simulate a completed payment.

### Get one payment

`GET /api/payments/{paymentId}`

### Get all payments

`GET /api/payments`

### Update payment status

`PATCH /api/payments/{paymentId}/status`

```json
{
  "status": "REFUNDED"
}
```

Valid statuses: `PENDING`, `SUCCESS`, `FAILED`, `REFUNDED`.

### Health check

`GET /actuator/health`

## Example curl

```bash
curl -X POST http://localhost:8080/api/payments \
  -H "Content-Type: application/json" \
  -d '{"customerId":"customer-101","amount":1500.00,"currency":"INR"}'
```

## Important production considerations

This is an educational starter project, not a production-ready payment processor. Before production use, add authentication and authorization, idempotency keys, provider-side payment verification, strict payment state-transition rules, database migrations, audit logging, rate limiting, secrets management, TLS, and integration tests. Never store card numbers, CVVs, or other sensitive payment credentials in this service.
