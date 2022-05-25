FROM maven:3.8.5-openjdk-17 AS build
COPY src /usr/rabbitmq-example-producer/src
COPY pom.xml /usr/rabbitmq-example-producer
RUN mvn -f /usr/rabbitmq-example-producer/pom.xml clean package

FROM openjdk:17-jdk-slim
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY --from=build /usr/rabbitmq-example-producer/${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]