# --- Stage 1: Build ---
FROM gradle:8.14-jdk21-alpine AS build
WORKDIR /app

# Copy dependency files first to leverage Docker layer caching
COPY build.gradle settings.gradle ./
COPY gradle gradle
COPY gradlew .
RUN ./gradlew dependencies --no-daemon || true

# Copy source code and build the executable jar
COPY src ./src
RUN ./gradlew bootJar -x test --no-daemon

# --- Stage 2: Run ---
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the compiled jar from the build stage
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]