# Parcial Práctico – Fullstack Reactive & Security

Backend Spring Boot **WebFlux (Mono/Flux)** + **Spring Security JWT** + **Arquitectura Hexagonal** + **R2DBC**.

## Requisitos
- Docker Desktop
- Docker Compose

## Levantar con Docker

```bash
docker compose up -d --build
```

- App: `http://localhost:8080`
- Actuator health: `GET /actuator/health`

### Elegir motor (doble motor)
Por defecto el `docker-compose.yml` levanta **PostgreSQL + MySQL** y la app usa **Postgres**:

- **Postgres**: `SPRING_PROFILES_ACTIVE=postgres`
- **MySQL**: `SPRING_PROFILES_ACTIVE=mysql`

Cambia eso en `docker-compose.yml` (servicio `app.environment.SPRING_PROFILES_ACTIVE`).

## Endpoints

### Auth (público)
- `POST /api/auth/register`
- `POST /api/auth/login`

### Jugadores
- `POST /api/jugadores` (ADMIN)
- `GET /api/jugadores` (ADMIN, USER)
- `GET /api/jugadores/{id}` (ADMIN, USER)
- `PUT /api/jugadores/{id}` (ADMIN)
- `DELETE /api/jugadores/{id}` (ADMIN)

### Tarjetas de penalización
- `POST /api/tarjetas` (ADMIN)
- `GET /api/tarjetas` (ADMIN, USER)
- `GET /api/tarjetas?jugadorId={uuid}` (ADMIN, USER)
- `GET /api/tarjetas/{id}` (ADMIN, USER)
- `PUT /api/tarjetas/{id}` (ADMIN)
- `DELETE /api/tarjetas/{id}` (ADMIN)

## Manejo de errores (JSON consistente)
Formato:

```json
{
  "timestamp": "2026-03-06T18:41:04.048Z",
  "status": 401,
  "error": "Unauthorized",
  "message": "No autenticado",
  "path": "/api/jugadores",
  "details": null
}
```

## Postman
Importa la colección:
- `postman/Parcial-Jugadores-Penalizaciones.postman_collection.json`

Incluye:
- Registro/Login ADMIN + USER
- CRUD Jugador + CRUD Tarjeta
- Pruebas de 401/403

