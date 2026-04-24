FROM openjdk:24-ea-17-jdk-slim-bullseye

# # WORKDIR /workspace

ARG JAR_FILE=../target/*.jar

COPY ${JAR_FILE} /var/lib/examples/backend/app.jar


ENTRYPOINT ["java","-jar", "/var/lib/examples/backend/app.jar"]

