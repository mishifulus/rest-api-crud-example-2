FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/rest-api-crud-example-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "rest-api-crud-example-0.0.1-SNAPSHOT.jar"]