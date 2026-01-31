# ========================
# Etapa de build
# ========================
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn package -DskipTests

# ========================
# Etapa de runtime
# ========================
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar application.jar

CMD ["java", "-jar", "application.jar"]
