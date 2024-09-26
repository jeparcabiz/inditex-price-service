# Inditex eCommerce Price application

## Description

Inditex eCommerce Price application, developed using Spring Boot, Hexagonal Architecture and TDD. This app use H2 database and Docker to configure developer environment.

## Requirements

- Docker
- Docker Compose
- Java 21
- Maven

## Configuration

1. Clone the repository

```bash
    git clone https://github.com/jeparcabiz/inditex-price-service.git
    cd inditex-price-service
```

2. Build Docker image and run container

```bash
    docker-compose up --build
```

3. Application is running in `http://localhost:8080`

## Run tests

To execute unit and acceptance tests, use next command:

```bash
    mvn clean compile test
```

## Endpoints

* `GET /v1/prices`: Retrieves price of product. You must specify applicationDate (with format 'yyyy-MM-ddTHH:mm:ssZ'), productId and brandId as queryParams.
Example URL: http://localhost:8080/v1/prices?applicationDate=2020-06-14T10:00:00Z&productId=35455&brandId=1

* `GET /swagger-ui.html`: Allows you to show OpenAPI contract.
