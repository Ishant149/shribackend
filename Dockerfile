FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /build

# Copy only the pom first to leverage Docker cache for dependencies
COPY pom.xml .
COPY src ./src

# Build the application (skip tests for faster builds on CI)
RUN mvn -B -DskipTests package

FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built jar from the builder stage
COPY --from=builder /build/target/*.jar app.jar

# Allow overriding JVM options via the JAVA_OPTS env var
ENV JAVA_OPTS=""

# Expose default HTTP port (Render will set $PORT env)
EXPOSE 8080

# Start the Spring Boot app and bind to Render-provided PORT if present
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -Dserver.port=${PORT:-8080} -jar /app/app.jar"]
