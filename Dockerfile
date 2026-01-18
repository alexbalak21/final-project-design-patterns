# Use latest OpenJDK (currently JDK 21) with slim OS for smaller image size
FROM openjdk:21-jdk-slim

# Set metadata for the image
LABEL maintainer="student" \
      description="Simple Inventory Management System" \
      version="1.0.0"

# Create a non-root user for security best practices
RUN groupadd -r appuser && useradd -r -g appuser appuser

# Set working directory inside container
WORKDIR /app

# Copy the JAR file from target directory
COPY target/simple-inventory-1.0.0.jar app.jar

# Change ownership to non-root user
RUN chown appuser:appuser app.jar

# Switch to non-root user
USER appuser

# Expose port (not really needed for command-line app, but good practice)
EXPOSE 8080

# Add health check (optional - shows advanced Docker practices)
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
    CMD echo "Application is running" || exit 1

# Command to run the application with optimized JVM settings
CMD ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
