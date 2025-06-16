FROM maven:3.8.4-openjdk-17-slim AS builder
ARG VERSION=0.0.1-SNAPSHOT
WORKDIR /build/
COPY pom.xml /build/
COPY src /build/src

RUN mvn clean package -DskipTests

FROM openjdk:17.0-jdk-slim
WORKDIR /app/
EXPOSE 8080
COPY --from=builder /build/target/EventSync-0.0.1-SNAPSHOT.jar /app/application.jar
CMD ["java", "-jar", "/app/application.jar"]
