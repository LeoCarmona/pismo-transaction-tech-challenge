# Base Image
FROM openjdk:8-jdk-alpine

# Maintainer
LABEL maintainer="Leonardo Carmona"

# Enable bash
RUN apk add bash

# Copy jar file to root folder
COPY *.jar app.jar

# Application entry point
ENTRYPOINT ["java", "-Xms256m", "-Xmx256m", "-jar", "/app.jar"]