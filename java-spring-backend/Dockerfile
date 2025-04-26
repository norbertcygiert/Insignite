FROM openjdk:17.0.14-jdk-slim

WORKDIR /Insignite

COPY . .

RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/insignite-0.0.1-SNAPSHOT.jar"]