FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app

COPY ./gradlew ./gradlew
COPY ./gradle ./gradle
COPY ./build.gradle.kts ./build.gradle.kts
COPY ./settings.gradle.kts ./settings.gradle.kts
COPY ./src ./src

RUN ./gradlew clean bootJar --no-daemon --stacktrace

FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

# Set database credentials via environment variables (defaults: admin / changeme)
ENV DB_USERNAME=admin \
    DB_PASSWORD=changeme

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
