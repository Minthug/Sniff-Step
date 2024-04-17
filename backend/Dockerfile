FROM amazoncorretto:17
WORKDIR /app
COPY . .
RUN ./gradlew clean build
RUN cp /app/build/libs/Sniff-Step-0.0.1-SNAPSHOT.jar /app/Sniff-Step-0.0.1-SNAPSHOT.jar
EXPOSE 8081
CMD ["java", "-jar", "Sniff-Step-0.0.1-SNAPSHOT.jar"]