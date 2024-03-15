FROM ubuntu:latest AS  build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

#FROM maven:3-eclipse-temurin-17 AS build
#COPY . .

RUN apt-get install maven -y
RUN mvn clean install -Pprod -DskipTests

FROM openjdk:17-jdk-slim

#FROM eclipse-temurin:17-alpine
#COPY --from=build /target/QuickMaster-1.0.0.jar demo.jar 
# <- changed file name

EXPOSE 8080

COPY --from=build /target/Lma-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar"]
#ENTRYPOINT ["java","-jar","demo.jar"]