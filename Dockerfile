# ---- Etapa 1: build ----
# Usa uma imagem com Maven + JDK 21 so pra compilar e gerar o .jar.
# Essa imagem nao vai pro resultado final, entao nao importa que seja grande.
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copia so o pom.xml primeiro e baixa as dependencias - assim, se so o codigo
# mudar (e nao as dependencias), o Docker reaproveita essa camada em cache
# e o build fica bem mais rapido nas proximas vezes.
COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src
RUN mvn -B clean package -DskipTests

# ---- Etapa 2: imagem final ----
# So o JRE (sem Maven, sem codigo fonte) + o .jar gerado. Bem menor que a de build.
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
