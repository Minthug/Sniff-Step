# Build stage
FROM amazoncorretto:17 AS builder
WORKDIR /app
COPY . .
RUN ./gradlew clean build

# Runtime stage
FROM amazoncorretto:17-jre
WORKDIR /app
COPY --from=builder /app/build/libs/Sniff-Step-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
CMD ["java", "-jar", "app.jar"]