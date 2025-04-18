# Etapa 1: Build con Maven
FROM maven:3.9.6-eclipse-temurin-17 AS builder

ARG MODULE

WORKDIR /app

# Copiar pom.xml y dependencias para el multimódulo
COPY pom.xml .
COPY .mvn/ .mvn
COPY mvnw .
COPY ${MODULE}/pom.xml ${MODULE}/pom.xml

RUN ./mvnw dependency:go-offline

# Copiar código fuente de todo el proyecto
COPY . .

# Ejecutar tests del módulo
RUN ./mvnw -pl ${MODULE} test

# Empaquetar el jar del módulo
RUN ./mvnw -pl ${MODULE} -am clean install -DskipTests=false

# Etapa 2: Imagen final
FROM eclipse-temurin:17-jre-alpine

ARG MODULE

WORKDIR /app

COPY --from=builder /app/${MODULE}/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]