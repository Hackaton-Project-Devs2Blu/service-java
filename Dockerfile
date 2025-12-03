FROM maven:3.9-eclipse-temurin-17-alpine AS builder
WORKDIR /app
COPY java-log-service/pom.xml .
RUN mvn dependency:go-offline
COPY java-log-service/src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
RUN chown appuser:appgroup app.jar
USER appuser
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
