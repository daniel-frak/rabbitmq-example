FROM maven:3.8.5-openjdk-17 AS build
COPY src /usr/rabbitmq-example-audit/src
COPY pom.xml /usr/rabbitmq-example-audit
RUN mvn -f /usr/rabbitmq-example-audit/pom.xml clean package

FROM openjdk:17-jdk-slim
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY --from=build /usr/rabbitmq-example-audit/${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]