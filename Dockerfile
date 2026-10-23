# ---- Build stage: compile and package the jar with Maven ----
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace

# Copy the POM first so dependency downloads are cached between builds
COPY pom.xml .
RUN mvn -B -q dependency:go-offline

COPY src ./src
RUN mvn -B package -DskipTests

# ---- Runtime stage: slim JRE image ----
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Run as a non-root user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=build /workspace/target/app.jar app.jar

ENV APP_VERSION=dev
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
