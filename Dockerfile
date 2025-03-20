# Use official Maven image to build the application
FROM maven:3.8.5-openjdk-17 AS build

# Set working directory
WORKDIR /app

# Copy project files to container
COPY . .

# Run Maven build to generate the JAR file
RUN mvn clean package -DskipTests

# Use a lightweight OpenJDK image for running the app
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the generated JAR from the previous build step
COPY --from=build /app/target/Jobs-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
