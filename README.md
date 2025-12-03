📘 Orders API – README
📦 Visão Geral

Orders API é uma aplicação REST desenvolvida em Spring Boot 3 + Java 21 para gerenciar pedidos.
Funcionalidades:

Criar pedidos (POST /orders)

Listar pedidos (GET /orders)

Buscar pedido por ID (GET /orders/{id})

Banco H2 em memória

Documentação via Swagger

Validações com Bean Validation

Testes unitários com MockMvc

Deploy via Docker

🚀 1. Pré-requisitos

Para rodar localmente, você precisa:

Java 21

Maven 3.9+

Para rodar via Docker:

Docker 24+

🧰 2. Rodando Localmente
▶️ Build do projeto
mvn clean package

▶️ Rodando a aplicação
mvn spring-boot:run


Ou:

java -jar target/orders-api-0.0.1-SNAPSHOT.jar

🌐 3. Endpoints Principais
Método	Endpoint	Descrição
POST	/orders	Cria um pedido
GET	/orders	Lista todos pedidos
GET	/orders/{id}	Busca pedido por ID
📄 4. Exemplo de JSON (POST /orders)
{
  "id": 1,
  "items": [
    { "sku": "SKU100", "quantity": 2, "price": 15.5 },
    { "sku": "SKU200", "quantity": 1, "price": 20.0 }
  ]
}

📚 5. Acessando o Swagger

Acesse após subir o app:

http://localhost:8080/swagger-ui/index.html


Documentação OpenAPI (JSON):

http://localhost:8080/v3/api-docs

🛢️ 6. Banco de Dados H2

Console H2 disponível em:

http://localhost:8080/h2-console


Configuração padrão:

JDBC URL: jdbc:h2:mem:ordersdb

User: sa

Password: vazio

🐳 7. Rodando via Docker

O projeto inclui o seguinte Dockerfile:

# Build Stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q dependency:go-offline
COPY src ./src
RUN mvn -q -DskipTests package

# Runtime Stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

📌 7.1 Construir a imagem
docker build -t orders-api .

📌 7.2 Rodar o container
docker run -p 8080:8080 orders-api

📌 7.3 Rodar em modo detach
docker run -d -p 8080:8080 --name orders orders-api

🔍 8. Testes Unitários

Execute:

mvn test


Os testes utilizam:

JUnit 5

Spring Boot Test

MockMvc
