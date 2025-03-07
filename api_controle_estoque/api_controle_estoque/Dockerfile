# Usar uma imagem oficial do Maven com OpenJDK 21 para compilar o projeto
FROM maven:3.8.5-openjdk-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Usar uma imagem oficial do OpenJDK 21 para rodar a aplicação
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
