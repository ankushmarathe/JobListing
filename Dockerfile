# Use an official OpenJDK runtime as the base image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR file from target folder to the container
COPY target/Jobs-0.0.1-SNAPSHOT.jar app.jar

# Expose port (same as in your application.properties)
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
