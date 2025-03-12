# =======================
# FASE 1: Build
# =======================
FROM openjdk:17-jdk-slim AS build

RUN apt-get update && apt-get install -y maven

WORKDIR /build

# Copiamo tutto il progetto
COPY . .

# Costruiamo tutto (compreso il modulo app)
RUN --mount=type=cache,target=/root/.m2 \
    mvn -Dmaven.repo.local=/root/.m2 clean install -DskipTests

# =======================
# FASE 2: Runtime
# =======================
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /build/app/target/app-1.0-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/app.jar"]
