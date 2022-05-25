FROM maven:3.8.5-openjdk-17 AS build
COPY src /usr/rabbitmq-example-worker/src
COPY pom.xml /usr/rabbitmq-example-worker
RUN mvn -f /usr/rabbitmq-example-worker/pom.xml clean package

FROM openjdk:17-jdk-slim
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY --from=build /usr/rabbitmq-example-worker/${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]