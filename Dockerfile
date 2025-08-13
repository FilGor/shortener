FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app

COPY ./gradlew ./gradlew
COPY ./gradle ./gradle
COPY ./src ./src

RUN ./gradlew clean bootJar --no-daemon --stacktrace

FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
