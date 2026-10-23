# cicd-training-2026

A very simple Spring Boot (Java 21, Maven) web app used to demonstrate how to build, containerise and deploy a web application through a CI/CD pipeline.

## Endpoints

| Method | Path                     | Response                                   |
|--------|--------------------------|--------------------------------------------|
| GET    | `/`                      | `Hello from cicd-training-2026!`           |
| GET    | `/api/hello?name=Alice`  | `{"message":"Hello, Alice!"}`              |
| GET    | `/health`                | `{"status":"UP","version":"<APP_VERSION>"}` |

## Run locally

Requires Java 21 and Maven 3.9+.

```bash
mvn test              # run the unit tests
mvn spring-boot:run   # start on http://localhost:8080
```

Optional: add a Maven wrapper so CI agents don't need Maven installed:

```bash
mvn wrapper:wrapper   # then use ./mvnw (or mvnw.cmd on Windows)
```

## Docker

```bash
docker build -t cicd-training-2026 .
docker run --rm -p 8080:8080 -e APP_VERSION=1.0.0 cicd-training-2026
```

Then open http://localhost:8080 or `curl http://localhost:8080/health`.

The Dockerfile is multi-stage: it builds the jar in a Maven image, then copies it into a slim JRE image that runs as a non-root user.

## Configuration

| Env var       | Default | Purpose                                  |
|---------------|---------|------------------------------------------|
| `PORT`        | `8080`  | HTTP port                                |
| `APP_VERSION` | `dev`   | Version reported by `/health` (set in CI) |
