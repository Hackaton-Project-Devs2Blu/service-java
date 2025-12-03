FROM --platform=$BUILDPLATFORM maven:3.9-amazoncorretto-17 AS builder
WORKDIR /app
COPY java-log-service/pom.xml .
RUN mvn dependency:go-offline
COPY java-log-service/src ./src
RUN mvn clean package -DskipTests -Dproject.build.sourceEncoding=UTF-8
FROM amazoncorretto:17-alpine
RUN apk add --no-cache shadow \
    && addgroup -S appgroup && adduser -S appuser -G appgroup
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
USER appuser
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
