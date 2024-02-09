FROM openjdk:17

WORKDIR /app

COPY gradle gradle
COPY gradlew build.gradle ./
COPY src ./src

RUN microdnf install findutils
RUN ./gradlew clean build -x test

CMD ["./gradlew", "bootRun"]
