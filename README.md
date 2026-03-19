# Advertising Platform (Microservices)

Simple advertising platform built using microservices architecture.
The system allows users to create products, launch ad campaigns, manage budget, and simulate ad clicks.

Frontend is a minimal admin panel built only to demonstrate backend functionality.

---

## Architecture

The project consists of the following services:

* API Gateway – single entry point for all requests
* Discovery Service (Eureka) – service registration and discovery
* Auth Service – authentication and JWT
* Campaign Service – campaigns, products, clicks
* Account Service – user balance and transactions
* Frontend (React) – admin dashboard

---

## Service Ports

| Service           | Port |
| ----------------- |------|
| API Gateway       | 8080 |
| Auth Service      | 8081 |
| Campaign Service  | 8082 |
| Account Service   | 8083 |
| Discovery Service | 8761 |
| Frontend          | 3001 |

---

## Architecture Notes

* All services register themselves in Eureka (Discovery Service)
* API Gateway routes requests to appropriate services
* Frontend communicates only with API Gateway
* Services communicate with each other via service discovery
* Actual branch - release

---

## Authentication

All protected endpoints require JWT token:

```id="authheader"
Authorization: Bearer <token>
```

---

## Swagger / API Documentation

Swagger UI is available for each service:

* Auth Service:

```id="swagger1"
http://localhost:8081/swagger-ui.html
```

* Campaign Service:

```id="swagger2"
http://localhost:8082/swagger-ui.html
```

* Account Service:

```id="swagger3"
http://localhost:8083/swagger-ui.html
```

If configured via API Gateway, Swagger can also be accessed through gateway routes.

---

## API Overview (via API Gateway)

Base URL:

```id="baseurl"
http://localhost:8080
```

---

## Auth Endpoints

### Register

```id="auth1"
POST /auth/register
```

### Login

```id="auth2"
POST /auth/login
```

### Verify Email

```id="auth3"
GET /auth/verify?token=...
```

---

## Campaign Endpoints

### Campaigns

```id="camp1"
GET /campaigns
POST /campaigns
PATCH /campaigns/{id}
DELETE /campaigns/{id}
GET /campaigns/{accountId}
```

---

### Products

```id="prod1"
GET /products
POST /products
PATCH /products/{id}
DELETE /products/{id}
```

---

### Clicks

```id="click1"
POST /clicks/{campaignId}
```

---

## Account Endpoints

```id="acc1"
GET /accounts/user
GET /accounts/balance
POST /accounts/deposit?amount=100
```

---

## Frontend

Tech stack:

* React
* TypeScript
* Vite
* Tailwind
* Axios

Pages:

* Dashboard
* Campaigns
* Products
* Account
* Login / Register

---

## How to Run

### 1. Clone repository

```id="clone"
git clone <repo-url>
cd project
```

---

### 2. Start Discovery Service (Eureka)

```id="eureka"
cd discovery-service
mvn spring-boot:run
```

Eureka UI:

```id="eurekaurl"
http://localhost:8761
```

---

### 3. Start API Gateway

```id="gateway"
cd api-gateway
mvn spring-boot:run
```

---

### 4. Start other services

Auth Service:

```id="authrun"
cd auth-service
mvn spring-boot:run
```

Campaign Service:

```id="camprun"
cd campaign-service
mvn spring-boot:run
```

Account Service:

```id="accrun"
cd account-service
mvn spring-boot:run
```

---

### 5. Run frontend

```id="frontend"
cd frontend
npm install
npm run dev
```

---

## Example Flow

1. Register user
2. Login and get JWT
3. Deposit money
4. Create product
5. Create campaign
6. Simulate clicks

---

## Notes

* All requests should go through API Gateway
* Services use Eureka for discovery
* Simplified implementation for demonstration purposes
* Minimal UI

---

## Future Improvements

* Docker and docker-compose setup
* Centralized logging
* Better error handling
* Production-ready security
* Monitoring (Prometheus, Grafana)

---
