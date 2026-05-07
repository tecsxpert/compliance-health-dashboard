# Compliance Health Dashboard

AI-powered compliance monitoring and analytics platform built using Spring Boot, React, Flask AI services, PostgreSQL, Redis, and Docker.

---

## Features

- JWT Authentication & RBAC
- Compliance Record Management
- AI-powered Recommendations
- AI-generated Reports
- Dashboard Analytics
- Audit Logging
- File Uploads
- Redis Caching
- Swagger API Docs
- Dockerized Full Stack Setup

---

## Tech Stack

### Backend

- Java 17
- Spring Boot 3
- Spring Security + JWT
- PostgreSQL
- Redis
- Flyway

### Frontend

- React + Vite
- Tailwind CSS
- Axios
- Recharts

### AI Service

- Python Flask
- Groq API
- ChromaDB
- sentence-transformers

---

## ASCII Architecture Diagram

```text
                +-------------------+
                |   React Frontend  |
                |  Vite + Tailwind  |
                +---------+---------+
                          |
                          v
                +-------------------+
                | Spring Boot API   |
                | JWT + REST APIs   |
                +----+---------+----+
                     |         |
          +----------+         +-----------+
          v                                v
+-------------------+          +-------------------+
| PostgreSQL DB     |          | Redis Cache       |
| Compliance Data   |          | API/AI Caching    |
+-------------------+          +-------------------+

                     |
                     v

            +-------------------+
            | Flask AI Service  |
            | Groq + ChromaDB   |
            +-------------------+
```

---

## Prerequisites

Install the following before setup:

- Java 17+
- Maven
- Node.js 18+
- Python 3.11+
- Docker Desktop
- Git

---

## Project Structure

```text
backend/
frontend/
ai-service/
docker-compose.yml
README.md
.env.example
```

---

## Setup Instructions

### 1. Clone Repository

```bash
git clone <your-repository-url>
cd compliance-health-dashboard
```

---

### 2. Configure Environment Variables

Create a `.env` file in the project root using `.env.example`.

Example:

```env
DB_NAME=compliance_db
DB_USER=postgres
DB_PASSWORD=postgres

REDIS_HOST=redis
REDIS_PORT=6379

JWT_SECRET=your-secret-key

GROQ_API_KEY=your-groq-api-key
```

---

### 3. Start Full Stack Using Docker

```bash
docker-compose up --build
```

---

## Application URLs

| Service     | URL                                   |
| ----------- | ------------------------------------- |
| Frontend    | http://localhost                      |
| Backend API | http://localhost:8080                 |
| Swagger UI  | http://localhost:8080/swagger-ui.html |
| AI Service  | http://localhost:5000/health          |

---

## Backend Setup (Without Docker)

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

---

## Frontend Setup

```bash
cd frontend
npm install
npm run dev
```

---

## AI Service Setup

```bash
cd ai-service

pip install -r requirements.txt

python app.py
```

---

## Running Tests

### Backend Tests

```bash
cd backend
mvn test
```

### Frontend Tests

```bash
npm test
```

### AI Tests

```bash
pytest
```

---

## Environment Variables

| Variable       | Description                        |
| -------------- | ---------------------------------- |
| DB_NAME        | PostgreSQL database name           |
| DB_USER        | PostgreSQL username                |
| DB_PASSWORD    | PostgreSQL password                |
| DB_HOST        | PostgreSQL host                    |
| DB_PORT        | PostgreSQL port                    |
| REDIS_HOST     | Redis server host                  |
| REDIS_PORT     | Redis server port                  |
| JWT_SECRET     | Secret key used for JWT generation |
| JWT_EXPIRATION | JWT expiration time                |
| MAIL_USERNAME  | SMTP email username                |
| MAIL_PASSWORD  | SMTP email password                |
| GROQ_API_KEY   | Groq API key for AI endpoints      |
| AI_SERVICE_URL | Flask AI service base URL          |
| VITE_API_URL   | Frontend backend API URL           |

---

## Security Notes

- Never commit `.env` files to GitHub
- All secrets must use environment variables
- JWT-secured APIs require Authorization headers
- Input validation and sanitisation enabled
- OWASP security testing performed

---

## Demo Features

- Create and manage compliance records
- AI-generated recommendations
- AI-powered reporting
- Dashboard KPIs and analytics
- CSV export
- Audit logging
- File upload support
- Role-based authentication

---

## Contributors

- Java Developer 1
- Java Developer 2
- Java Developer 3
- AI Developer 1
- AI Developer 2
- AI Developer 3
- Security Reviewer

---
