FROM maven:3.6.1-jdk-8-alpine AS builder
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY --from=builder /usr/src/app/target/*.jar /app/app.jar
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar","/app/app.jar"]

