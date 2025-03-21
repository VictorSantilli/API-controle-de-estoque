# Usa a imagem do Maven para compilar a aplicação
FROM maven:3.9.7-amazoncorretto-17 AS build

WORKDIR /app

# Copia apenas os arquivos essenciais primeiro para evitar erros
COPY pom.xml .
COPY src ./src

# Executa a build (sem cache para evitar erro no Railway)
RUN mvn clean package -DskipTests

# Usa uma imagem mais leve para rodar a aplicação
FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

# Copia o JAR gerado na fase de build
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta da aplicação
EXPOSE 8080

# Comando para rodar o Spring Boot
CMD ["java", "-jar", "app.jar"]
