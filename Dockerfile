 

# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/employee-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]