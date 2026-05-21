# --- Stage 1: Build Stage ---
# Use the official Gradle image with Java 21 to compile the project
FROM gradle:8.14.4-jdk21-alpine AS build
WORKDIR /app

# Copy Gradle configuration files to leverage Docker layer caching
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Copy the application source code
COPY src ./src

# Build the application generating the .jar file (skipping tests for a faster build)
RUN ./gradlew bootJar -x test

# --- Stage 2: Run Stage ---
# Use a lightweight Eclipse Temurin Java 21 JRE image for production execution
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the compiled .jar file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Command to execute the reactive application
ENTRYPOINT ["java", "-jar", "app.jar"]