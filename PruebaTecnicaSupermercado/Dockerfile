FROM maven:3.9.6-eclipse-temurin-21-jammy
ARG JAR_FILE=target/PruebaTecnicaSupermercado-0.0.1.jar
COPY ${JAR_FILE} app_pruebatecsuper.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app_pruebatecsuper.jar"]