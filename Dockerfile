# Usa una imagen con JDK y Maven preinstalado
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean install -DskipTests

# Segunda fase: solo el JAR
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/reporte-jasper-postgres-soloconpython-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]