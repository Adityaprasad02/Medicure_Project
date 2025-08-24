# Use official OpenJDK 21 Alpine image as base image
FROM eclipse-temurin:21-jdk-alpine

# Set working directory inside container
WORKDIR /app

# Copy the Spring Boot fat jar to the container
COPY target/project_03-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080, Spring Boot ka default port
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
