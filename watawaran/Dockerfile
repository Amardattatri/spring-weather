# Stage 1: Build the application
# Use an official OpenJDK runtime as a parent image
FROM maven:3.8.6-openjdk-11 AS builder
# Set the working directory in the container
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the final image
FROM openjdk:11-jdk-slim
WORKDIR /app
# Copy the JAR file into the container
COPY --from=builder /app/target/watawaran-0.0.1-SNAPSHOT.jar app.jar
# Expose the port your application runs on
EXPOSE 8080
# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
