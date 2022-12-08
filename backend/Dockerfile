FROM openjdk:17-jdk as build
WORKDIR /workspace/app

COPY gradle gradle
COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY src src

RUN microdnf install findutils
RUN ./gradlew build -x test

FROM openjdk:17-jdk
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/build/libs
COPY --from=build ${DEPENDENCY}/*.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
