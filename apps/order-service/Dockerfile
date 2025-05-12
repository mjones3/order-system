# Stage 1: Build the application using Maven
FROM maven:3.8.5-openjdk-17-slim AS builder
WORKDIR .

# Copy the Maven descriptor and download dependencies (cache layer)
COPY pom.xml .
#RUN mvn dependency:go-offline -B

# Copy the rest of the application source code
COPY src ./src

# Build the application and skip tests
RUN mvn package -DskipTests -B

# Stage 2: Create the runtime image
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the jar file from the builder stage
COPY --from=builder /target/order-service-0.0.1-SNAPSHOT.jar ./order-service.jar

# Expose the port that the Spring Boot application listens on (default 8080)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-Dspring.main.debug=true", "-jar", "order-service.jar"]
