FROM maven:3.9.9-eclipse-temurin-21-alpine as builder
COPY . /tmp
WORKDIR /tmp
RUN mvn clean package

FROM eclipse-temurin:21-jre as app
VOLUME /tmp
COPY --from=builder /tmp/target/price-service-1.0.0.jar price-service.jar
ENTRYPOINT ["java","-jar","/price-service.jar"]