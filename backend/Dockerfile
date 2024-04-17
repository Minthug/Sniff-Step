FROM amazoncorretto:17
WORKDIR /app
COPY . .
RUN ./gradlew clean build
COPY build/libs/*.jar app.jar
RUN chmod +x app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]