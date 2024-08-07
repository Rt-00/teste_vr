# Etapa 1: Construir o JAR do aplicativo usando Maven
FROM maven:3.9.8-amazoncorretto-21-al2023 AS build

# Diretório de trabalho dentro do contêiner
WORKDIR /app

# Copiar o arquivo pom.xml e os arquivos de código-fonte para o contêiner
COPY pom.xml .
COPY src ./src

# Executar o comando para construir o JAR do aplicativo
RUN mvn clean package -DskipTests

# Etapa 2: Criar a imagem do contêiner com o JAR do aplicativo
FROM maven:3.9.8-amazoncorretto-21-al2023

# Diretório de trabalho dentro do contêiner
WORKDIR /app

# Copiar o JAR construído da etapa anterior
COPY target/server-0.0.1-SNAPSHOT.jar ./app.jar

# Expor a porta em que o aplicativo está escutando
EXPOSE 8080

# Comando para executar o aplicativo
ENTRYPOINT ["java", "-jar", "app.jar"]