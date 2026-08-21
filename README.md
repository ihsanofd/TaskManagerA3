# TaskManager — A3: Containerize your stack

A Spring Boot Task Manager API backed by PostgreSQL, running Postgres in Docker.
This is the third storage swap in the FlyRank Backend Track series (in-memory → SQLite → containerized Postgres).

## Tech stack

- **Language:** Java 21 (Spring Boot 4.0)
- **Framework:** Spring Boot (Web MVC, Data JPA)
- **Database:** PostgreSQL 16, running in Docker
- **Build tool:** Maven (via `mvnw`)
- **Driver:** PostgreSQL JDBC driver

## Project structure

```
src/main/java/com/ihsan/TaskManager/
├── Controller/     # REST endpoints
├── Dto/            # Request/response objects
├── Entity/         # Task JPA entity
├── Enum/           # TaskPriority, TaskStatus
├── Exception/      # Custom exceptions
├── Repository/     # Spring Data JPA repository
└── Service/        # Business logic
```

## Prerequisites

- [Docker Desktop](https://www.docker.com/products/docker-desktop/) (or Podman)
- Java 21+
- No local PostgreSQL install running on port 5432 (it will conflict with the container — see Troubleshooting)

## Setup & run

### 1. Clone the repo

```bash
git clone https://github.com/ihsanofd/TaskManagerA3.git
cd TaskManagerA3
```

### 2. Start Postgres in Docker

```bash
docker run --name taskdb -e POSTGRES_PASSWORD=dev -e POSTGRES_DB=tasks -p 5432:5432 -v taskdata:/var/lib/postgresql/data -d postgres:16
```

Verify it's running:

```bash
docker ps
docker exec -it taskdb psql -U postgres -d tasks -c "\dt"
```

### 3. Configure environment variables

Copy `.env.example` to `.env` and fill in your values:

```bash
cp .env.example .env
```

`.env.example`:
```
DB_URL=jdbc:postgresql://localhost:5432/tasks
DB_USER=postgres
DB_PASSWORD=your_password_here
```

### 4. Set environment variables and run the app

**PowerShell:**
```powershell
$env:DB_URL="jdbc:postgresql://localhost:5432/tasks"
$env:DB_USER="postgres"
$env:DB_PASSWORD="dev"
.\mvnw.cmd spring-boot:run
```

**macOS/Linux:**
```bash
export DB_URL=jdbc:postgresql://localhost:5432/tasks
export DB_USER=postgres
export DB_PASSWORD=dev
./mvnw spring-boot:run
```

The app starts on `http://localhost:8080`. On first run, Hibernate creates the `task` table automatically (`ddl-auto=update`).

## API endpoints

| Method | Endpoint         | Description          | Success | Not found |
|--------|------------------|-----------------------|---------|-----------|
| GET    | `/tasks`         | List all tasks        | 200     | —         |
| GET    | `/tasks/{id}`    | Get a single task     | 200     | 404       |
| POST   | `/tasks`         | Create a task          | 201     | —         |
| PUT    | `/tasks/{id}`    | Update a task          | 200     | 404       |
| DELETE | `/tasks/{id}`    | Delete a task          | 204     | 404       |

### Example request

```bash
curl -i -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"Buy milk","description":"2% milk, one gallon","priority":"LOW","status":"TODO","dueDate":"2026-08-25"}'
```

## Verifying data in Postgres

```bash
docker exec -it taskdb psql -U postgres -d tasks -c "SELECT * FROM task;"
```

<!-- Add a screenshot of your psql output or a GUI tool (DBeaver/pgAdmin/TablePlus) here -->

## Troubleshooting

**"password authentication failed for user postgres"**
This usually means a local (non-Docker) Postgres install is already listening on port 5432 and intercepting the connection, instead of the Docker container. Check with:
```bash
netstat -ano | findstr :5432       # Windows
lsof -i :5432                      # macOS/Linux
```
If a local Postgres service is running, stop it (`net stop postgresql-x64-<version>` on Windows, as Administrator) or run the container on a different port.

## Project status

- [x] Postgres running in a Docker container
- [x] App connects via `.env` (git-ignored, no hardcoded credentials)
- [x] `task` table auto-created on first run
- [x] `.env.example` committed with placeholder values
- [x] `POST` and `GET` verified against Postgres
- [ ] `PUT` and `DELETE` verified end-to-end
- [ ] Single-command startup with `docker compose up` (Dockerfile + compose.yaml)
- [ ] Persistence proof (`docker compose down` → `up`, data survives)
- [ ] Multi-stage Dockerfile / image size comparison (stretch)
- [ ] AI rematch (bonus stage)

## License

For educational purposes — FlyRank Internship, Backend Track, Week 1, Assignment A3.