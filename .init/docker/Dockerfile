# -------------------------------------------
# Etapa 1: Build con Maven (solo módulo)
# -------------------------------------------
FROM maven:3.9.6-eclipse-temurin-21 AS builder

ARG MODULE
WORKDIR /app

# 1) Copiamos el POM padre y el wrapper (no usaremos mvnw, pero es buena práctica incluirlo)
COPY pom.xml ./
COPY .mvn/ .mvn
COPY mvnw mvnw

# 2) Copiamos el POM del módulo específico y su carpeta src
COPY ${MODULE}/pom.xml ${MODULE}/pom.xml
COPY ${MODULE}/src ${MODULE}/src

# 3) Descargamos dependencias (offline) usando mvn en vez de mvnw
RUN mvn -f ${MODULE}/pom.xml dependency:go-offline \
    -Dproject.build.sourceEncoding=UTF-8 -B

# 4) Compilamos y empaquetamos ÚNICAMENTE este módulo (sin mvnw)
RUN mvn -f ${MODULE}/pom.xml clean package \
    -DskipTests -Dproject.build.sourceEncoding=UTF-8 -B

# -------------------------------------------
# Etapa 2: Runtime con solo JRE
# -------------------------------------------
FROM eclipse-temurin:21-jre-alpine

ARG MODULE
WORKDIR /app

# 5) Copiamos el JAR resultante del módulo desde la etapa “builder”
COPY --from=builder /app/${MODULE}/target/*.jar app.jar

# 6) Exponemos el puerto 8080 (Spring Boot usa 8080 por defecto)
EXPOSE 8080

# 7) Arrancamos la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
