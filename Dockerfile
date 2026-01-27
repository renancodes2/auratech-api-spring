FROM maven:4.0.0-rc-5-eclipse-temurin-21-noble as build

WORKDIR /build

COPY . .

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build ./build/target/*.jar ./app.jar

EXPOSE 8080

ENV TZ='America/Sao_Paulo'

ENV JAVA_OPTS="-Xmx350X -Xms256M"

ENTRYPOINT ["java", "-jar", "app.jar"]