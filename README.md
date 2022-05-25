[![Code Soapbox Logo](readme-images/codesoapbox_logo.svg)](https://codesoapbox.dev/)

# RabbitMQ Example

This is an example of a RabbitMQ setup with 3 microservices:

- a producer
- a worker
- an audit service

## Getting Started

[TBD]

The microservices will be available under the following URLs:
- producer - http://localhost:8080
- worker - http://localhost:8081
- audit - http://localhost:8082

## Code analysis

You can perform local code analysis of this project using SonarQube.

### Prerequisites

1. Go to the `./docker` folder
2. Execute `docker-compose -f docker-compose-sonar.yml up -d` to start a local Sonarqube instance
3. Visit http://localhost:9000/
4. Log in with the credentials `admin:admin`
5. Update the password when prompted

### Running the analysis

You can run the analysis using the following command in the repository root:

```shell
mvn clean verify sonar:sonar -Pcode-coverage -Dsonar.login=your_username -Dsonar.password=your_password
```

After a successful analysis, the report will be available at http://localhost:9000/projects