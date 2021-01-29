FROM maven:3.6.1-jdk-8-alpine AS builder
COPY . /usr/src
WORKDIR /usr/src
RUN mvn clean package -pl pong -am

FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY --from=builder /usr/src/pong/target/*.jar /app/app.jar
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar","/app/app.jar"]

